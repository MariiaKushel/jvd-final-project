package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

public class CreateNewAccountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);

        Map<String, String> userData = new HashMap<>();
        userData.put(EMAIL, email);
        userData.put(NAME, name);
        userData.put(PHONE_NUMBER, phoneNumber);
        userData.put(REPEAT_PASSWORD, repeatPassword);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if (service.createNewAccount(userData, password)) {
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.SING_IN_PAGE, REDIRECT);
            } else {
                request.setAttribute(TEMP_EMAIL, userData.get(EMAIL));
                request.setAttribute(TEMP_NAME, userData.get(NAME));
                request.setAttribute(TEMP_PHONE_NUMBER,userData.get(PHONE_NUMBER));
                request.setAttribute(TEMP_REPEAT_PASSWORD,userData.get(REPEAT_PASSWORD));
                request.setAttribute(TEMP_PASSWORD,password);

                request.setAttribute(WRONG_EMAIL, userData.get(WRONG_EMAIL));
                request.setAttribute(WRONG_EMAIL_EXIST, userData.get(WRONG_EMAIL_EXIST));
                request.setAttribute(WRONG_NAME, userData.get(WRONG_NAME));
                request.setAttribute(WRONG_PHONE_NUMBER, userData.get(WRONG_PHONE_NUMBER));
                request.setAttribute(WRONG_REPEAT_PASSWORD, userData.get(WRONG_REPEAT_PASSWORD));
                request.setAttribute(WRONG_PASSWORD, userData.get(WRONG_PASSWORD));
                ///
                //session.setAttribute(SessionAttribute.WRONG_MESSAGE, true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CREATE_NEW_ACCOUNT_PAGE);
                commandResult = new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute CreateNewAccountCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
