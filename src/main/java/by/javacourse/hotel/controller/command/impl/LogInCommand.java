package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

import static by.javacourse.hotel.controller.command.SessionAtribute.*;

import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogInCommand implements Command {

    static Logger logger = LogManager.getLogger();
    private static final String MESSAGE = "Wrong login or password";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            if (service.authenticate(login, password)) {
                session.setAttribute(CURRENT_USER, login);
                commandResult = CommandResult.createRedirectCommandResult(PagePath.RESULT_PAGE);
            } else {
                commandResult = CommandResult.createRedirectCommandResult(PagePath.LOG_IN_PAGE
                        + SIMBOL_QUESTION + WRONG_LOGIN_PASSWORD_MESSAGE + SIMBOL_EQUALS + MESSAGE);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute LogInCommand was failed " + e);
            commandResult = CommandResult.createRedirectCommandResult(PagePath.ERROR_500_PAGE); //TODO add error code
        }
        return commandResult;
    }
}
