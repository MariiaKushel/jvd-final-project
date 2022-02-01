package by.javacourse.hotel.exception;

/**
 * {@code CommandException} class represent a checked exception from {@link by.javacourse.hotel.controller.command}
 * @see Exception
 */
public class CommandException extends Exception {
    public CommandException() {

        super();
    }

    public CommandException(String message) {

        super(message);
    }

    public CommandException(Exception e) {

        super(e);
    }

    public CommandException(String message, Exception e) {

        super(message, e);
    }
}
