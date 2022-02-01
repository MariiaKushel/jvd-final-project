package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.CommandException;
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

public class CreateNewAccountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = (Map<String, String>) session.getAttribute(USER_DATA_SES);

        cleanWrongMessage(userData);
        updateUserDataFromRequest(request, userData);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            int sizeBefore = userData.size();
            boolean result = service.createNewAccount(userData);
            int sizeAfter = userData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(USER_DATA_SES);
                session.setAttribute(CREATE_NEW_ACCOUNT_RESULT, result);
            }else{
                session.setAttribute(USER_DATA_SES, userData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.CREATE_NEW_ACCOUNT_PAGE);
            commandResult = new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CreateNewAccountCommand was failed" + e);
            throw new CommandException("Try to execute CreateNewAccountCommand was failed", e);
        }
        return commandResult;
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(EMAIL_SES, request.getParameter(EMAIL));
        userData.put(NAME_SES, request.getParameter(NAME));
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
        userData.remove(WRONG_MISMATCH_SES);
    }
}
