package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.controller.command.RequestParameter;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class UpdatePersonalDataCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        Map<String, String> userData = extractUserData(request, session);
        System.out.println("userData b " +userData);
        CommandResult commandResult = null;
        try {
            Optional<User> optUser = userService.updatePersonalData(userData);

            System.out.println("userData af " +userData);
            if (optUser.isPresent()) {
                session.removeAttribute(EMAIL_SES);
                session.removeAttribute(NAME_SES);
                session.removeAttribute(PHONE_NUMBER_SES);
                session.removeAttribute(ROLE_SES);
                session.removeAttribute(USER_STATUS_SES);
                session.removeAttribute(BALANCE_SES);
                session.removeAttribute(RATE_SES);
                session.removeAttribute(DISCOUNT_ID_SES);
                session.removeAttribute(PASSWORD_SES);
                session.removeAttribute(WRONG_NAME_SES);
                session.removeAttribute(WRONG_PHONE_NUMBER_SES);
                session.removeAttribute(WRONG_PASSWORD_SES);

                session.setAttribute(UPDATE_PERSONAL_DATA_RESULT, true);
            }else{
                session.setAttribute(EMAIL_SES, userData.get(EMAIL));
                session.setAttribute(NAME_SES, userData.get(NAME));
                session.setAttribute(PHONE_NUMBER_SES, userData.get(PHONE_NUMBER));
                session.setAttribute(ROLE_SES, userData.get(ROLE));
                session.setAttribute(USER_STATUS_SES, userData.get(USER_STATUS));
                session.setAttribute(BALANCE_SES, userData.get(BALANCE));
                session.setAttribute(RATE_SES, request.getParameter(RATE));
                session.setAttribute(DISCOUNT_ID_SES, request.getParameter(DISCOUNT_ID));
                session.setAttribute(PASSWORD_SES, userData.get(PASSWORD));
                session.setAttribute(WRONG_NAME_SES, userData.get(WRONG_NAME_SES));
                session.setAttribute(WRONG_PHONE_NUMBER_SES, userData.get(WRONG_PHONE_NUMBER_SES));
                session.setAttribute(WRONG_PASSWORD_SES, userData.get(WRONG_PASSWORD_SES));

                session.removeAttribute(UPDATE_PERSONAL_DATA_RESULT);
                //session.setAttribute(UPDATE_PERSONAL_DATA_RESULT, false);
            }

            session.setAttribute(CURRENT_PAGE, PagePath.ACCOUNT_PAGE);
            commandResult = new CommandResult(PagePath.ACCOUNT_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdatePersonalDataCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }


        return commandResult;
    }

    private Map<String, String> extractUserData(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute(CURRENT_USER_ID).toString();
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String role = request.getParameter(ROLE);
        String status = request.getParameter(USER_STATUS);
        String balance = request.getParameter(BALANCE);
        String discountId = request.getParameter(DISCOUNT_ID);
        String password = request.getParameter(PASSWORD);

        Map<String, String> userData = new HashMap<>();
        userData.put(CURRENT_USER_ID, userId);
        userData.put(EMAIL, email);
        userData.put(NAME, name);
        userData.put(PHONE_NUMBER, phoneNumber);
        userData.put(ROLE, role);
        userData.put(USER_STATUS, status);
        userData.put(BALANCE, balance);
        userData.put(DISCOUNT_ID, discountId);
        userData.put(PASSWORD, password);
        return userData;
    }
}
