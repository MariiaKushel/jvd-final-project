package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.controller.command.SessionAtribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;

public class GoToSingInPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAtribute.CURRENT_PAGE, PagePath.SING_IN_PAGE);
        return new CommandResult(PagePath.SING_IN_PAGE, FORWARD);
    }
}
