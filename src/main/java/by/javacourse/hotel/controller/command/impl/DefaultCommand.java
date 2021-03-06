package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_ROLE;

public class DefaultCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CommandResult commandResult = null;

        if (session.getAttribute(CURRENT_ROLE) != null) {
            session.setAttribute(CURRENT_PAGE, PagePath.HOME_PAGE);
            commandResult = new CommandResult(PagePath.HOME_PAGE, REDIRECT);
        } else {
            session.setAttribute(CURRENT_PAGE, PagePath.MAIN_PAGE);
            commandResult = new CommandResult(PagePath.MAIN_PAGE, REDIRECT);
        }
        return commandResult;
    }
}
