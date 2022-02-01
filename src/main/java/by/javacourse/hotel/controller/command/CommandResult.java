package by.javacourse.hotel.controller.command;

/**
 * {@code CommandResult} class represent complex form response of {@link Command}
 * It includes the page to which the transition should be made and sending type.
 */
public class CommandResult {

    /**
     * {@code SendingType} enum represent a sending type
     */
    public enum SendingType {
        FORWARD, REDIRECT
    }

    private String page;
    private SendingType sendingType;

    public CommandResult(String page, SendingType sendingType) {
        this.page = page;
        this.sendingType = sendingType;
    }

    public String getPage() {
        return page;
    }

    public SendingType getSendingType() {
        return sendingType;
    }
}
