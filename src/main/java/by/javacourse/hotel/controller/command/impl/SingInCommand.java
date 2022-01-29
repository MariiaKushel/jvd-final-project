package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class SingInCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SES);

        cleanWrongMessage(userData);
        updateUserDataFromRequest(request, userData);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            Optional<User> optUser = service.authenticate(userData);
            if (optUser.isPresent()) {
                User user = optUser.get();
                session.removeAttribute(USER_DATA_SES);
                session.setAttribute(CURRENT_EMAIL, user.getEmail());
                session.setAttribute(CURRENT_USER_ID, user.getEntityId());
                session.setAttribute(CURRENT_ROLE, user.getRole().toString());
                session.setAttribute(CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.HOME_PAGE, REDIRECT);
            } else {
                session.setAttribute(CURRENT_PAGE, PagePath.SING_IN_PAGE);
                commandResult = new CommandResult(PagePath.SING_IN_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute SingInCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(EMAIL_SES, request.getParameter(EMAIL));
        userData.put(PASSWORD_SES, request.getParameter(PASSWORD));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_EMAIL_OR_PASSWORD_SES);
        userData.remove(NOT_FOUND_SES);
    }
}
