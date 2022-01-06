package by.javacourse.hotel.controller.command;

public enum CommandName {
    CHANGE_LOCALE("change_locale"),

    SING_IN("sing_in"),
    SING_OUT("sing_out"),
    CREATE_NEW_ACCOUNT("create_new_account"),

    GO_TO_MAIN_PAGE("go_to_main_page"),
    GO_TO_SING_IN_PAGE("go_to_sing_in_page"),
    GO_TO_CREATE_NEW_ACCOUNT_PAGE("go_to_create_new_account_page"),
    GO_TO_HOME_PAGE("go_to_home_page"),
    GO_TO_FIND_ROOM_PAGE("go_to_find_room_page"),
    GO_TO_CONTACT_PAGE("go_to_contact_page"),
    GO_TO_ACCOUNT_PAGE("go_to_account_page");

    private String webCommandName;

    private CommandName(String webCommandName) {
        this.webCommandName = webCommandName;
    }

}
