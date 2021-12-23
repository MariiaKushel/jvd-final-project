package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.entity.User;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

public class RegistrationCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String MESSAGE = "Wrong data or such email already in use";
    private static final User.Role DEFAULT_ROLE = User.Role.CLIENT;
    private static final User.Status DEFAULT_STATUS = User.Status.NEW;
    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("0");
    private static final long DEFAULT_DISCOUNT_ID = 1;

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);

        User newUser = new User(); //TODO use builder or factory method
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRole(DEFAULT_ROLE);
        newUser.setStatus(DEFAULT_STATUS);
        newUser.setBalance(DEFAULT_BALANCE);
        newUser.setDiscountId(DEFAULT_DISCOUNT_ID);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if (service.register(newUser)) {
                commandResult = CommandResult.createRedirectCommandResult(PagePath.LOG_IN_PAGE);
            } else {
                commandResult = CommandResult.createRedirectCommandResult(PagePath.REGESTRATION_PAGE
                        + SIMBOL_QUESTION + WRONG_DATA_MESSAGE + SIMBOL_EQUALS + MESSAGE);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute RegistrationCommand was failed" + e);
            commandResult = CommandResult.createRedirectCommandResult(PagePath.ERROR_500_PAGE); //TODO add error code
        }

        return commandResult;
    }
}
