package by.javacourse.hotel.controller.command;

public class CommandResult {
    public enum SendingType {
        FORWARD, REDIRECT, ERROR
    }

    private String page;
    private SendingType sendingType;
    private int errorCode;

    public CommandResult(String page, SendingType sendingType) {
        this.page = page;
        this.sendingType = sendingType;
    }

    public CommandResult(String page, SendingType sendingType, int errorCode) {
        this.page = page;
        this.sendingType = sendingType;
        if (sendingType == SendingType.ERROR){
            this.errorCode = errorCode;
        }
    }

    public String getPage() {
        return page;
    }

    public SendingType getSendingType() {
        return sendingType;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
