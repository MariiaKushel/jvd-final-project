package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

public class FindRoomByParameterCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        updateSearchParametersFromRequest(request, searchParameters);
        String[] sleepingPlaces = request.getParameterValues(SLEEPING_PLACES);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        List<Room> rooms = new ArrayList<>();
        CommandResult commandResult = null;
        try {
            rooms = roomService.findRoomByParameters(searchParameters, sleepingPlaces);
            request.setAttribute(ROOM_LIST_ATR, rooms);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.BOOK_ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindRoomByParameter was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

    private void updateSearchParametersFromRequest(HttpServletRequest request, Map<String, String> searchParameters) {
        searchParameters.put(DATE_FROM_ATR, request.getParameter(DATE_FROM));
        searchParameters.put(DATE_TO_ATR, request.getParameter(DATE_TO));
        searchParameters.put(PRICE_FROM_ATR, request.getParameter(PRICE_FROM));
        searchParameters.put(PRICE_TO_ATR, request.getParameter(PRICE_TO));
    }
}
