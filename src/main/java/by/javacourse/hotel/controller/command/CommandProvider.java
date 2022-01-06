package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.controller.command.impl.*;
import by.javacourse.hotel.controller.command.impl.relocation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

public final class CommandProvider {
    static Logger logger = LogManager.getLogger();

    private static EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    static {
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());

        commands.put(CommandName.SING_IN, new SingInCommand());
        commands.put(CommandName.SING_OUT, new SingOutCommand());
        commands.put(CommandName.CREATE_NEW_ACCOUNT, new CreateNewAccountCommand());

        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.GO_TO_SING_IN_PAGE, new GoToSingInPageCommand());
        commands.put(CommandName.GO_TO_CREATE_NEW_ACCOUNT_PAGE, new GoToCreateNewAccountPageCommand());
        commands.put(CommandName.GO_TO_HOME_PAGE, new GoToHomePageCommand());
        commands.put(CommandName.GO_TO_FIND_ROOM_PAGE, new GoToFindRoomPageCommand());
        commands.put(CommandName.GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(CommandName.GO_TO_ACCOUNT_PAGE, new GoToAccountPageCommand());
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
