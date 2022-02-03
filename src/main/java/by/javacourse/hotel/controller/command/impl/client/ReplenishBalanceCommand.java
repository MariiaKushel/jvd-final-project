package by.javacourse.hotel.controller.command.impl.client;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class ReplenishBalanceCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> balanceData = (Map<String, String>) session.getAttribute(BALANCE_DATA_SES);

        cleanWrongMessage(balanceData);
        updateBalanceDataFromRequest(request, balanceData);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        CommandResult commandResult = null;
        try {
            int sizeBefore = balanceData.size();
            boolean result = userService.replenishBalance(balanceData);
            int sizeAfter = balanceData.size();
            if (sizeBefore == sizeAfter){
                session.setAttribute(REPLENISH_BALANCE_RESULT, result);
                session.removeAttribute(BALANCE_DATA_SES);
            }else{
                session.setAttribute(BALANCE_DATA_SES, balanceData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.REPLENISH_BALANCE_PAGE);
            commandResult = new CommandResult(PagePath.REPLENISH_BALANCE_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute ReplenishBalanceCommand was failed " + e);
             throw new CommandException("Try to execute ReplenishBalanceCommand was failed ", e);
        }
        return commandResult;
    }

    private void updateBalanceDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(REPLENISH_AMOUNT_SES, request.getParameter(REPLENISH_AMOUNT));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_AMOUNT_OVERSIZE_SES);
        userData.remove(WRONG_AMOUNT_SES);
    }
}
