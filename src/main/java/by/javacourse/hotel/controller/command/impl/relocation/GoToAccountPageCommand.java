package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.controller.command.SessionAtribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;

public class GoToAccountPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAtribute.CURRENT_PAGE, PagePath.ACCOUNT_PAGE);
        return new CommandResult(PagePath.ACCOUNT_PAGE, FORWARD);
    }
}
