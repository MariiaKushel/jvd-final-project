package by.javacourse.hotel.controller.command.impl.common.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.USER_ID;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToAccountPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(UPDATE_PERSONAL_DATA_RESULT);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        DiscountService discountService = provider.getDiscountService();

        User.Role userRole = User.Role.valueOf(session.getAttribute(CURRENT_ROLE).toString());
        String userId = userRole == User.Role.ADMIN
                ? request.getParameter(USER_ID)
                : session.getAttribute(CURRENT_USER_ID).toString();

        CommandResult commandResult = null;
        try {
            Optional<User> optUser = userService.findUserById(userId);
            if (optUser.isPresent()) {
                User user = optUser.get();
                String discountId = String.valueOf(user.getDiscountId());
                Optional<Discount> discount = discountService.findDiscountById(discountId);
                Map<String, String> userData = createUserDataMap(user, discount);
                session.setAttribute(USER_DATA_SES, userData);
                session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            } else {
                session.setAttribute(NOT_FOUND_SES, true);
            }
            commandResult = userRole == User.Role.ADMIN
                    ? new CommandResult(PagePath.ACCOUNT_ADMIN_PAGE, REDIRECT)
                    : new CommandResult(PagePath.ACCOUNT_CLIENT_PAGE, REDIRECT);

        } catch (ServiceException e) {
            logger.error("Try to execute GoToAccountPageCommand was failed " + e);
            throw new CommandException("Try to execute GoToAccountPageCommand was failed ", e);
        }
        return commandResult;
    }

    private Map<String, String> createUserDataMap(User user, Optional<Discount> discount) {
        Map<String, String> userData = new HashMap<>();
        userData.put(USER_ID_SES, String.valueOf(user.getEntityId()));
        userData.put(EMAIL_SES, user.getEmail());
        userData.put(NAME_SES, user.getName());
        userData.put(PHONE_NUMBER_SES, user.getPhoneNumber());
        userData.put(ROLE_SES, user.getRole().toString());
        userData.put(USER_STATUS_SES, user.getStatus().toString());
        userData.put(BALANCE_SES, user.getBalance().toString());
        userData.put(DISCOUNT_ID_SES, String.valueOf(user.getDiscountId()));
        if (discount.isPresent()) {
            userData.put(RATE_SES, String.valueOf(discount.get().getRate()));
        }
        return userData;
    }
}
