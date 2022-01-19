package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class CreateNewAccountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        Map<String, String> userData = extractUserData(request);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();
        HttpSession session = request.getSession();
        CommandResult commandResult = null;
        try {
            if (service.createNewAccount(userData)) {
                session.removeAttribute(EMAIL_SES);
                session.removeAttribute(NAME_SES);
                session.removeAttribute(PHONE_NUMBER_SES);
                session.removeAttribute(PASSWORD_SES);
                session.removeAttribute(REPEAT_PASSWORD_SES);

                session.removeAttribute(WRONG_EMAIL_SES);
                session.removeAttribute(WRONG_EMAIL_EXIST_SES);
                session.removeAttribute(WRONG_NAME_SES);
                session.removeAttribute(WRONG_PHONE_NUMBER_SES);
                session.removeAttribute(WRONG_REPEAT_PASSWORD_SES);
                session.removeAttribute(WRONG_PASSWORD_SES);

                session.setAttribute(CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.SING_IN_PAGE, REDIRECT);
            } else {
                session.setAttribute(EMAIL_SES, userData.get(EMAIL));
                session.setAttribute(NAME_SES, userData.get(NAME));
                session.setAttribute(PHONE_NUMBER_SES, userData.get(PHONE_NUMBER));
                session.setAttribute(PASSWORD_SES, userData.get(PASSWORD));
                session.setAttribute(REPEAT_PASSWORD_SES, userData.get(REPEAT_PASSWORD));

                session.setAttribute(WRONG_EMAIL_SES, userData.get(WRONG_EMAIL_SES));
                session.setAttribute(WRONG_EMAIL_EXIST_SES, userData.get(WRONG_EMAIL_EXIST_SES));
                session.setAttribute(WRONG_NAME_SES, userData.get(WRONG_NAME_SES));
                session.setAttribute(WRONG_PHONE_NUMBER_SES, userData.get(WRONG_PHONE_NUMBER_SES));
                session.setAttribute(WRONG_REPEAT_PASSWORD_SES, userData.get(WRONG_REPEAT_PASSWORD_SES));
                session.setAttribute(WRONG_PASSWORD_SES, userData.get(WRONG_PASSWORD_SES));

                session.setAttribute(CURRENT_PAGE, PagePath.CREATE_NEW_ACCOUNT_PAGE);
                commandResult = new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute CreateNewAccountCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }

    private Map<String, String> extractUserData(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);

        Map<String, String> userData = new HashMap<>();
        userData.put(EMAIL, email);
        userData.put(NAME, name);
        userData.put(PHONE_NUMBER, phoneNumber);
        userData.put(PASSWORD, password);
        userData.put(REPEAT_PASSWORD, repeatPassword);
        return userData;
    }
}
