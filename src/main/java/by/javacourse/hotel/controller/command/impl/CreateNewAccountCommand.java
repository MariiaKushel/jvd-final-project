package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateNewAccountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SES);
        cleanWrongMessage(userData);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if (service.createNewAccount(userData)) {
                session.removeAttribute(USER_DATA_SES);
                session.setAttribute(CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.SING_IN_PAGE, REDIRECT);
            } else {
                session.setAttribute(CURRENT_PAGE, PagePath.CREATE_NEW_ACCOUNT_PAGE);
                commandResult = new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute CreateNewAccountCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(EMAIL_SES, request.getParameter(EMAIL));
        userData.put(NAME_SES,request.getParameter(NAME));
        userData.put(PHONE_NUMBER_SES, request.getParameter(PHONE_NUMBER));
        userData.put(PASSWORD_SES, request.getParameter(PASSWORD));
        userData.put(REPEAT_PASSWORD_SES, request.getParameter(REPEAT_PASSWORD));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_EMAIL_SES);
        userData.remove(WRONG_EMAIL_EXIST_SES);
        userData.remove(WRONG_NAME_SES);
        userData.remove(WRONG_PHONE_NUMBER_SES);
        userData.remove(WRONG_PASSWORD_SES);
        userData.remove(WRONG_REPEAT_PASSWORD_SES);
        userData.remove(WRONG_MISSMATCH_SES);
    }
}
