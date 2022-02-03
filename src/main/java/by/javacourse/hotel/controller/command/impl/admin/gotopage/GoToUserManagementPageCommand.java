package by.javacourse.hotel.controller.command.impl.admin.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Stream;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.NEW_SEARCH_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToUserManagementPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        List<String> statuses = Stream.of(User.Status.values())
                .map(e -> e.name())
                .toList();
        List<String> roles = Stream.of(User.Role.values())
                .map(e -> e.name())
                .toList();

        ServiceProvider provider = ServiceProvider.getInstance();
        DiscountService discountService = provider.getDiscountService();

        CommandResult commandResult = null;
        try {
            List<Discount> discounts = discountService.findAllDiscount();
            request.setAttribute(NEW_SEARCH_ATR, true);
            session.setAttribute(ALL_DISCOUNTS_SES, discounts);
            session.setAttribute(ALL_USER_STATUSES_SES, statuses);
            session.setAttribute(ALL_USER_ROLES_SES, roles);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.USER_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToUserManagementPageCommand was failed" + e);
             throw new CommandException("Try to execute GoToUserManagementPageCommand was failed", e);
        }
        return commandResult;
    }
}
