package by.javacourse.hotel.controller.command;

public enum CommandName {
    CHANGE_LOCALE("change_locale"),

    SING_IN("sing_in"),
    SING_OUT("sing_out"),
    CREATE_NEW_ACCOUNT("create_new_account"),

    FIND_ALL_ROOMS("find_all_rooms"),
    FIND_ALL_VISIBLE_ROOMS("find_all_visible_rooms"),
    FIND_ROOM_BY_ID("find_room_by_id"),
    FIND_ROOM_BY_NUMBER("find_room_by_number"),
    FIND_ROOM_BY_SLEEPING_PLACE("find_room_by_sleeping_place"),
    FIND_ROOM_BY_PRICE_RANGE("find_room_by_price_range"),
    FIND_ROOM_BY_VISIBLE("find_room_by_visible"),

    ADD_NEW_IMAGE("add_new_image"),
    FIND_IMAGES_BY_ROOM("find_images_by_room"),
    FIND_PREVIEW_IMAGE("find_preview_image"),
    FIND_ROOM_BY_PARAMETER("find_room_by_parameter"),
    CREATE_ORDER("create_order"),
    UPDATE_PERSONAL_DATA("update_personal_data"),
    CHANGE_PASSWORD("change_password"),
    REPLENISH_BALANCE("replenish_balance"),
    CANCEL_ORDER("cancel_order"),
    UPDATE_ORDER("update_order"),
    CREATE_REVIEW("create_review"),
    UPDATE_REVIEW("update_review"),
    FIND_USER_BY_PARAMETER("find_user_by_parameter"),
    FIND_ALL_ORDERS("find_all_orders"),
    FIND_ORDER_BY_PREPAYMENT("find_order_by_prepayment"),
    FIND_ORDER_BY_STATUS("find_order_by_status"),
    FIND_ORDER_BY_DATE_RANGE("find_order_by_date_range"),
    FIND_ORDER_BY_USER_ID("find_order_by_user_id"),
    FIND_ORDER_BY_USER_ID_LAST("find_order_by_user_id_last"),
    FIND_ALL_REVIEWS("find_all_reviews"),
    FIND_REVIEW_BY_DATE_RANGE("find_review_by_date_range"),
    FIND_ALL_DISCOUNTS("find_all_discounts"),
    FIND_DISCOUNT_BY_RATE("find_discount_by_rate"),

    GO_TO_MAIN_PAGE("go_to_main_page"),
    GO_TO_SING_IN_PAGE("go_to_sing_in_page"),
    GO_TO_CREATE_NEW_ACCOUNT_PAGE("go_to_create_new_account_page"),
    GO_TO_HOME_PAGE("go_to_home_page"),
    GO_TO_BOOK_ROOM_PAGE("go_to_book_room_page"),
    GO_TO_CONTACT_PAGE("go_to_contact_page"),
    GO_TO_ACCOUNT_PAGE("go_to_account_page"),
    GO_TO_ORDER_PAGE("go_to_order_page"),
    GO_TO_CANCEL_ORDER_PAGE("go_to_cancel_order_page"),
    GO_TO_UPDATE_ORDER_PAGE("go_to_update_order_page"),
    GO_TO_CHANGE_PASSWORD_PAGE("go_to_change_password_page"),
    GO_TO_USER_MANAGEMENT_PAGE("go_to_user_management_page"),
    GO_TO_ROOM_MANAGEMENT_PAGE("go_to_room_management_page"),
    GO_TO_REPLENISH_BALANCE_PAGE("go_to_replenish_balance_page"),
    GO_TO_ORDER_MANAGEMENT_PAGE("go_to_order_management_page"),
    GO_TO_REVIEW_MANAGEMENT_PAGE("go_to_review_management_page"),
    GO_TO_DISCOUNT_MANAGEMENT_PAGE("go_to_discount_management_page"),
    GO_TO_CLIENT_ORDERS_PAGE("go_to_client_orders_page"),
    GO_TO_CREATE_REVIEW_PAGE("go_to_create_review_page"),
    GO_TO_UPDATE_REVIEW_PAGE("go_to_update_review_page");

    private String webCommandName;

    private CommandName(String webCommandName) {
        this.webCommandName = webCommandName;
    }

}
