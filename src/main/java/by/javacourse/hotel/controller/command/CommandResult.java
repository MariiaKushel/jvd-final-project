package by.javacourse.hotel.controller.command;

public class CommandResult {
    public enum SendingType {
        FORWARD, REDIRECT, ERROR
    }

    private String page;
    private SendingType sendingType;

    private CommandResult(String page, SendingType sendingType) {
        this.page = page;
        this.sendingType = sendingType;
    }
    public static CommandResult createForwardCommandResult (String page){
        return new CommandResult(page, SendingType.FORWARD);
    }
    public static CommandResult createRedirectCommandResult (String page){
        return new CommandResult(page, SendingType.REDIRECT);
    }
    public static CommandResult createErrorCommandResult (String page){ //TODO add error code
        return new CommandResult(page, SendingType.ERROR);
    }

    public String getPage() {
        return page;
    }

    public SendingType getSendingType() {
        return sendingType;
    }
}
