package by.javacourse.hotel.controller.command.impl.common;

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

import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UpdatePersonalDataCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SES);

        cleanWrongMessage(userData);
        updateUserDataByRequest(request, userData);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        CommandResult commandResult = null;
        try {
            int sizeBefore = userData.size();
            boolean result = userService.updatePersonalData(userData);
            int sizeAfter = userData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(USER_DATA_SES);
                session.setAttribute(UPDATE_PERSONAL_DATA_RESULT, result);
            } else {
                session.setAttribute(USER_DATA_SES, userData);
            }

            User.Role role = User.Role.valueOf(session.getAttribute(CURRENT_ROLE).toString());
            if (role == User.Role.ADMIN) {
                session.setAttribute(CURRENT_PAGE, PagePath.ACCOUNT_ADMIN_PAGE);
                commandResult = new CommandResult(PagePath.ACCOUNT_ADMIN_PAGE, REDIRECT);
            } else {
                session.setAttribute(CURRENT_PAGE, PagePath.ACCOUNT_CLIENT_PAGE);
                commandResult = new CommandResult(PagePath.ACCOUNT_CLIENT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute UpdatePersonalDataCommand was failed" + e);
             throw new CommandException("Try to execute UpdatePersonalDataCommand was failed", e);
        }
        return commandResult;
    }

    private void updateUserDataByRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(NAME_SES, request.getParameter(NAME));
        userData.put(PHONE_NUMBER_SES, request.getParameter(PHONE_NUMBER));
        userData.put(ROLE_SES, request.getParameter(ROLE));
        userData.put(USER_STATUS_SES, request.getParameter(USER_STATUS));
        userData.put(DISCOUNT_ID_SES, request.getParameter(DISCOUNT_ID));
        userData.put(PASSWORD_SES, request.getParameter(PASSWORD));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_PASSWORD_SES);
        userData.remove(WRONG_NAME_SES);
        userData.remove(WRONG_PHONE_NUMBER_SES);
        userData.remove(PASSWORD_SES);
    }
}
