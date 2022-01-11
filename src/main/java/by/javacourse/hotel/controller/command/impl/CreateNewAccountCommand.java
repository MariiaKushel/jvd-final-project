package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.controller.command.SessionAttribute;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;

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
                session.setAttribute(SessionAttribute.WRONG_MESSAGE, false);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.SING_IN_PAGE, REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.WRONG_MESSAGE, true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CREATE_NEW_ACCOUNT_PAGE);
                commandResult = new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute CreateNewAccountCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
