package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    CommandResult execute (HttpServletRequest request) throws CommandException;
}
