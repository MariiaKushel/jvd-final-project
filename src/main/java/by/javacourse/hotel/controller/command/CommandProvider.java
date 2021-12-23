package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.controller.command.impl.*;
import command.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static Map<String, Command> commands = new HashMap<>(); //TODO static ok?

    static { //TODO static ok?
        commands.put(CommandName.LOG_IN, new LogInCommand());
        commands.put(CommandName.REGESTRATION, new RegistrationCommand());
        commands.put(CommandName.GO_TO_WELCOME_PAGE, new GoToWelcomPageCommand());
        commands.put(CommandName.GO_TO_LOG_IN_PAGE, new GoToLogInPageCommand());
        commands.put(CommandName.GO_TO_REGESTRATION_PAGE, new GoToRegistrationPageCommand());
    }

    public static Command getCommand(String commandName) { //TODO static ok?
        Command currentCommand = commands.get(commandName);
        return currentCommand != null ? currentCommand : new DefaultCommand();
    }
}
