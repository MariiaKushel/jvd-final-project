package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.controller.command.impl.*;
import by.javacourse.hotel.controller.command.impl.relocation.GoToCreateNewAccountPageCommand;
import by.javacourse.hotel.controller.command.impl.relocation.GoToMainPageCommand;
import by.javacourse.hotel.controller.command.impl.relocation.GoToSingInPageCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

public final class CommandProvider {
    static Logger logger = LogManager.getLogger();

    private static EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    static {
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());

        commands.put(CommandName.SING_IN, new SingInCommand());
        commands.put(CommandName.CREATE_NEW_ACCOUNT, new CreateNewAccountCommand());

        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.GO_TO_SING_IN_PAGE, new GoToSingInPageCommand());
        commands.put(CommandName.GO_TO_CREATE_NEW_ACCOUNT_PAGE, new GoToCreateNewAccountPageCommand());

    }

    public static Command getCommand(String commandName) {
        Command currentCommand;
        try {
            logger.debug(commandName.toUpperCase());
            currentCommand = commands.get(CommandName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.error("Command: " + commandName + ", is not present " + e);
            currentCommand = new DefaultCommand();
        }
        return currentCommand;
    }
}
