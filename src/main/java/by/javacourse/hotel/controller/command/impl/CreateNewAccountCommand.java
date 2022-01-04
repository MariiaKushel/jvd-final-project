package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

public class CreateNewAccountCommand implements Command {
    static Logger logger = LogManager.getLogger();
    private static final String MESSAGE = "Wrong data or such email already in use";//FIXME

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);

        Map<String, String> userData = new HashMap<>();
        userData.put(EMAIL, email);
        userData.put(NAME, name);
        userData.put(PHONE_NUMBER, phoneNumber);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if (service.createNewAccount(userData, password).isPresent()) {
                commandResult = CommandResult.createRedirectCommandResult(PagePath.SING_IN_PAGE);
            } else {
                commandResult = CommandResult.createRedirectCommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE
                        + SIMBOL_QUESTION + WRONG_DATA_MESSAGE + SIMBOL_EQUALS + MESSAGE);//FIXME
            }
        } catch (ServiceException e) {
            logger.error("Try to execute RegistrationCommand was failed" + e);
            commandResult = CommandResult.createRedirectCommandResult(PagePath.ERROR_500_PAGE); //TODO add error code
        }

        return commandResult;
    }
}
