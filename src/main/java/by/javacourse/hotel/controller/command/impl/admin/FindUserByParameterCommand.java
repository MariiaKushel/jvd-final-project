package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class FindUserByParameterCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        updateSearchParametersFromRequest(request, searchParameters);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        List<User> users = new ArrayList<>();
        CommandResult commandResult = null;
        try {
            users = userService.findUserByParameter(searchParameters);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            request.setAttribute(USER_LIST_ATR, users);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.USER_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindRoomByParameter was failed " + e);
             throw new CommandException("Try to execute FindRoomByParameter was failed ", e);
        }
        return commandResult;
    }

    private void updateSearchParametersFromRequest(HttpServletRequest request, Map<String, String> searchParameters) {
        searchParameters.put(EMAIL_ATR, request.getParameter(EMAIL));
        searchParameters.put(PHONE_NUMBER_ATR, request.getParameter(PHONE_NUMBER));
        searchParameters.put(NAME_ATR, request.getParameter(NAME));
        searchParameters.put(USER_STATUS_ATR, request.getParameter(USER_STATUS));
    }
}
