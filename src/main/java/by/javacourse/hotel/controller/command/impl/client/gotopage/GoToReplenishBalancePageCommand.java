package by.javacourse.hotel.controller.command.impl.client.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToReplenishBalancePageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(REPLENISH_BALANCE_RESULT);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        String userId = session.getAttribute(CURRENT_USER_ID).toString();
        CommandResult commandResult = null;
        try {
            Optional<User> optUser = userService.findUserById(userId);
            if (optUser.isPresent()) {
                User user = optUser.get();
                BigDecimal balance = user.getBalance();
                Map<String, String> balanceData = new HashMap<>();
                balanceData.put(BALANCE_SES, balance.toString());
                balanceData.put(USER_ID_SES, session.getAttribute(CURRENT_USER_ID).toString());
                session.setAttribute(BALANCE_DATA_SES, balanceData);
            } else {
                session.setAttribute(NOT_FOUND_SES, true);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.REPLENISH_BALANCE_PAGE);
            commandResult = new CommandResult(PagePath.REPLENISH_BALANCE_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToReplenishBalancePageCommand was failed " + e);
             throw new CommandException("Try to execute GoToReplenishBalancePageCommand was failed ", e);
        }
        return commandResult;
    }

}
