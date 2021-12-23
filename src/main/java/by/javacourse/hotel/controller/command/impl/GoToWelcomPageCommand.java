package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class GoToWelcomPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createRedirectCommandResult(PagePath.WELCOME_PAGE);
    }
}
