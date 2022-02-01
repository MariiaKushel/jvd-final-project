package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class FindRoomByPriceRangeCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(PRICE_FROM_ATR, request.getParameter(PRICE_FROM));
        searchParameters.put(PRICE_TO_ATR, request.getParameter(PRICE_TO));
        System.out.println("searchParameters 1" +searchParameters);
        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService service = provider.getRoomService();
        CommandResult commandResult = null;
        try {
            List<Room> rooms = service.findRoomByPriceRange(searchParameters);
            request.setAttribute(ROOM_LIST_ATR, rooms);
            System.out.println("searchParameters 2" +searchParameters);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.ROOM_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindAllRoomsCommand was failed " + e);
             throw new CommandException("Try to execute FindAllRoomsCommand was failed ", e);
        }
        return commandResult;
    }

    private void updateSearchParametersFromRequest(HttpServletRequest request, Map<String, String> searchParameters) {
        searchParameters.put(ROOM_NUMBER_ATR, request.getParameter(ROOM_NUMBER));
    }
}
