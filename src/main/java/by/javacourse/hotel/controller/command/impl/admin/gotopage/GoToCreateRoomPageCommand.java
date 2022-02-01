package by.javacourse.hotel.controller.command.impl.admin.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToCreateRoomPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CREATE_ROOM_RESULT);
        Map<String, String> roomData = new HashMap<>();
        session.setAttribute(ROOM_DATA_SES, roomData);
        session.setAttribute(CURRENT_PAGE, PagePath.CREATE_ROOM_PAGE);
        return new CommandResult(PagePath.CREATE_ROOM_PAGE, REDIRECT);
    }
}
