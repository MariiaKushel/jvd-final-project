package by.javacourse.hotel.controller.command;

public class CommandResult {
    public enum SendingType {
        FORWARD, REDIRECT, ERROR
    }

    private String page;
    private SendingType sendingType;
   /* private int errorCode;
    private String message;*/

    public CommandResult(String page, SendingType sendingType) {
        this.page = page;
        this.sendingType = sendingType;
    }
/*
    public CommandResult(String page, SendingType sendingType, int errorCode, String message) {
        this.page = page;
        this.sendingType = sendingType;
        if (sendingType == SendingType.ERROR){
            this.errorCode = errorCode;
            this.message = message;
        }
    }*/

    public String getPage() {
        return page;
    }

    public SendingType getSendingType() {
        return sendingType;
    }

    /*public int getErrorCode() {
        return errorCode;
    }

    public String getMessage(){
        return  message;
    }*/
}
