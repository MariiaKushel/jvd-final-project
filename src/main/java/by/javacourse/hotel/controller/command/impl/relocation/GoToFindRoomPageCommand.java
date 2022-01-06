package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.controller.command.SessionAtribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;

public class GoToFindRoomPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAtribute.CURRENT_PAGE, PagePath.FIND_ROOM_PAGE);
        return new CommandResult(PagePath.FIND_ROOM_PAGE, FORWARD);
    }
}
