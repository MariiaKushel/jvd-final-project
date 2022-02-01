package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * {@code Command} interface represent functional of command
 */
public interface Command {

    /**
     * Execute command
     * @param request - request from controller, type {@link CommandResult}
     * @throws CommandException - if service method throw {@link by.javacourse.hotel.exception.ServiceException}
     */
    CommandResult execute (HttpServletRequest request) throws CommandException;
}
