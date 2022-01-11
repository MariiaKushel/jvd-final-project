package by.javacourse.hotel.controller.command;

public enum CommandName {
    CHANGE_LOCALE("change_locale"),

    SING_IN("sing_in"),
    SING_OUT("sing_out"),
    CREATE_NEW_ACCOUNT("create_new_account"),

    FIND_ALL_ROOMS("find_all_rooms"),
    FIND_ALL_VISIBLE_ROOMS("find_all_visible_rooms"),
    FIND_ROOM_BY_ID("find_room_by_id"),

    ADD_NEW_IMAGE("add_new_image"),
    FIND_IMAGES_BY_ROOM("find_images_by_room"),
    FIND_PREVIEW_IMAGE("find_preview_image"),


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
