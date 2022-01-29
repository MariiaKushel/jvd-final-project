package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ChangePasswordCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>)session.getAttribute(USER_DATA_SES);

        cleanWrongMessage(userData);
        updateUserDataFromRequest(request, userData);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if(userService.changePassword(userData)){
                session.removeAttribute(USER_DATA_SES);
                session.setAttribute(CHANGE_PASSWORD_RESULT, true);
            }else{
                session.removeAttribute(CHANGE_PASSWORD_RESULT);
                session.setAttribute(USER_DATA_SES, userData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.CHANGE_PASSWORD_PAGE);
            commandResult = new CommandResult(PagePath.CHANGE_PASSWORD_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute ChangePasswordCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(PASSWORD_SES, request.getParameter(PASSWORD));
        userData.put(NEW_PASSWORD_SES, request.getParameter(NEW_PASSWORD));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_PASSWORD_SES);
        userData.remove(WRONG_NEW_PASSWORD_SES);
        userData.remove(WRONG_OLD_PASSWORD_SES);
    }
}
