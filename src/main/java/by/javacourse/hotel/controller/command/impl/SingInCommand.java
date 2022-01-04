package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;

import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SingInCommand implements Command {

    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService service = provider.getUserService();

        CommandResult commandResult = null;
        try {
            Optional<User> optUser = service.authenticate(email, password);
            if (optUser.isPresent()) {
                session.setAttribute(SessionAtribute.CURRENT_USER, email);
                User user = optUser.get();
                String role = user.getRole().toString();
                session.setAttribute(SessionAtribute.CURRENT_ROLE, role);
                session.setAttribute(SessionAtribute.WRONG_MESSAGE, false);
                commandResult = CommandResult.createRedirectCommandResult(PagePath.HOME_PAGE);
            } else {
                session.setAttribute(SessionAtribute.WRONG_MESSAGE, true);
                commandResult = CommandResult.createRedirectCommandResult(PagePath.SING_IN_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute SingInCommand was failed " + e);
            commandResult = CommandResult.createRedirectCommandResult(PagePath.ERROR_500_PAGE); //TODO add error code
        }
        return commandResult;
    }
}
