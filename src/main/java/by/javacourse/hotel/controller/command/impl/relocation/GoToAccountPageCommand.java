package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestAttribute.RATE_ATR;
import static by.javacourse.hotel.controller.command.RequestAttribute.USER_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToAccountPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        String userId = session.getAttribute(CURRENT_USER_ID).toString();

        session.removeAttribute(UPDATE_PERSONAL_DATA_RESULT);
        session.removeAttribute(EMAIL_SES);
        session.removeAttribute(NAME_SES);
        session.removeAttribute(PHONE_NUMBER_SES);
        session.removeAttribute(ROLE_SES);
        session.removeAttribute(USER_STATUS_SES);
        session.removeAttribute(BALANCE_SES);
        session.removeAttribute(RATE_SES);
        session.removeAttribute(DISCOUNT_ID_SES);
        session.removeAttribute(PASSWORD_SES);
        session.removeAttribute(WRONG_NAME_SES);
        session.removeAttribute(WRONG_PHONE_NUMBER_SES);
        session.removeAttribute(WRONG_PASSWORD_SES);

        CommandResult commandResult = null;
        try{
            Optional<User> user = userService.findUserById(userId);
            if(user.isPresent()){
                int rate = userService.findDiscountByUserId(userId);
                request.setAttribute(RATE_ATR, rate);
                request.setAttribute(USER_ATR, user.get());
                session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
                commandResult = new CommandResult(PagePath.ACCOUNT_PAGE, FORWARD);
            }else{
                commandResult = new CommandResult(PagePath.MAIN_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute GoToAccountPageCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
