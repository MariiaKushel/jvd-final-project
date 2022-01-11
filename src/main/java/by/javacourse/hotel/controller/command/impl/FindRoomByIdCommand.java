package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;

public class FindRoomByIdCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();

        HttpSession session = request.getSession();
        long roomId = Long.parseLong(request.getParameter(RequestParameter.ROOM_ID));
        logger.debug("room id> " + roomId);
        Optional<Room> room = Optional.empty();
        CommandResult commandResult = null;

        try {
            room = roomService.findRoomById(roomId);

            logger.debug("room in res> " + room);
            if (room.isPresent()) {
                request.setAttribute(RequestAttribute.ROOM, room.get());
                session.setAttribute(SessionAttribute.WRONG_MESSAGE, false);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
                commandResult = new CommandResult(PagePath.ROOM_PAGE, FORWARD);
            } else {
                session.setAttribute(SessionAttribute.WRONG_MESSAGE, true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
                commandResult = new CommandResult(PagePath.ROOM_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute FindRoomByIdCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
