package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UpdateRoomCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> roomData = (Map<String, String>) session.getAttribute(ROOM_DATA_SES);

        cleanWrongMessage(roomData);
        updateUserDataFromRequest(request, roomData);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService service = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            int sizeBefore = roomData.size();
            boolean result = service.updateRoom(roomData);
            int sizeAfter = roomData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(ROOM_DATA_SES);
                session.setAttribute(UPDATE_ROOM_RESULT, result);
            } else {
                session.setAttribute(ROOM_DATA_SES, roomData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateRoomCommand was failed" + e);
             throw new CommandException("Try to execute UpdateRoomCommand was failed", e);
        }
        return commandResult;
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(ROOM_NUMBER_SES, request.getParameter(ROOM_NUMBER));
        userData.put(SLEEPING_PLACE_SES, request.getParameter(SLEEPING_PLACE));
        userData.put(PRICE_SES, request.getParameter(PRICE));
        String[] vis = request.getParameterValues(VISIBLE);
        if (vis != null) {
            userData.put(VISIBLE_SES, request.getParameterValues(VISIBLE)[0]);
        }
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_NUMBER_SES);
        userData.remove(WRONG_SLEEPING_PLACE_SES);
        userData.remove(WRONG_PRICE_SES);
    }
}
