package by.javacourse.hotel.controller.command.impl.common;

import by.javacourse.hotel.controller.command.*;
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

import java.util.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestAttribute.ROOM_LIST_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;


public class FindAllVisibleRoomsCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            List<Room> rooms = roomService.findAllVisibleRooms();
            request.setAttribute(ROOM_LIST_ATR, rooms);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.SHOW_ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindAllVisibleRoomsCommand was failed " + e);
            throw new CommandException("Try to execute FindAllVisibleRoomsCommand was failed ", e);
        }
        return commandResult;
    }
}
