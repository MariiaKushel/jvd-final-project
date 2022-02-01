package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.entity.User;

import java.util.EnumSet;

import static by.javacourse.hotel.entity.User.Role.*;

/**
 * {@code CommandName} enum represent all commands,
 * contains jsp command name and user roles, who can call that command
 */
public enum CommandName {

    ADD_NEW_IMAGE("add_new_image", EnumSet.of(ADMIN)),
    CANCEL_ORDER("cancel_order", EnumSet.of(CLIENT)),
    CHANGE_LOCALE("change_locale", EnumSet.of(GUEST, CLIENT, ADMIN)),
    CHANGE_PASSWORD("change_password", EnumSet.of(CLIENT, ADMIN)),
    CHANGE_PREVIEW("change_preview", EnumSet.of(ADMIN)),
    CREATE_DISCOUNT("create_discount", EnumSet.of(ADMIN)),
    CREATE_NEW_ACCOUNT("create_new_account", EnumSet.of(GUEST)),
    CREATE_ORDER("create_order", EnumSet.of(CLIENT, ADMIN)),
    CREATE_REVIEW("create_review", EnumSet.of(CLIENT)),
    CREATE_ROOM("create_room", EnumSet.of(ADMIN)),
    FIND_ALL_DISCOUNTS("find_all_discounts", EnumSet.of(ADMIN)),
    FIND_ALL_ORDERS("find_all_orders", EnumSet.of(ADMIN)),
    FIND_ALL_REVIEWS("find_all_reviews", EnumSet.of(ADMIN)),
    FIND_ALL_ROOMS("find_all_rooms", EnumSet.of(ADMIN)),
    FIND_ALL_VISIBLE_ROOMS("find_all_visible_rooms", EnumSet.of(GUEST, CLIENT, ADMIN)),
    FIND_DISCOUNT_BY_RATE("find_discount_by_rate", EnumSet.of(ADMIN)),
    FIND_ORDER_BY_DATE_RANGE("find_order_by_date_range", EnumSet.of(ADMIN)),
    FIND_ORDER_BY_PREPAYMENT("find_order_by_prepayment", EnumSet.of(ADMIN)),
    FIND_ORDER_BY_STATUS("find_order_by_status", EnumSet.of(ADMIN)),
    FIND_ORDER_BY_USER_ID("find_order_by_user_id", EnumSet.of(CLIENT)),
    FIND_ORDER_BY_USER_ID_LAST("find_order_by_user_id_last", EnumSet.of(CLIENT)),
    FIND_REVIEW_BY_DATE_RANGE("find_review_by_date_range", EnumSet.of(ADMIN)),
    FIND_ROOM_BY_ID("find_room_by_id", EnumSet.of(GUEST, CLIENT, ADMIN)),
    FIND_ROOM_BY_NUMBER("find_room_by_number", EnumSet.of(ADMIN)),
    FIND_ROOM_BY_PARAMETER("find_room_by_parameter", EnumSet.of(CLIENT, ADMIN)),
    FIND_ROOM_BY_PRICE_RANGE("find_room_by_price_range", EnumSet.of(ADMIN)),
    FIND_ROOM_BY_SLEEPING_PLACE("find_room_by_sleeping_place", EnumSet.of(ADMIN)),
    FIND_ROOM_BY_VISIBLE("find_room_by_visible", EnumSet.of(ADMIN)),
    FIND_USER_BY_PARAMETER("find_user_by_parameter", EnumSet.of(ADMIN)),
    GO_TO_ACCOUNT_PAGE("go_to_account_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_BOOK_ROOM_PAGE("go_to_book_room_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_CANCEL_ORDER_PAGE("go_to_cancel_order_page", EnumSet.of(CLIENT)),
    GO_TO_CHANGE_PASSWORD_PAGE("go_to_change_password_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_CLIENT_ORDERS_PAGE("go_to_client_orders_page", EnumSet.of(CLIENT)),
    GO_TO_CONTACT_PAGE("go_to_contact_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_CREATE_DISCOUNT_PAGE("go_to_create_discount_page", EnumSet.of(ADMIN)),
    GO_TO_CREATE_NEW_ACCOUNT_PAGE("go_to_create_new_account_page", EnumSet.of(GUEST)),
    GO_TO_CREATE_REVIEW_PAGE("go_to_create_review_page", EnumSet.of(CLIENT)),
    GO_TO_CREATE_ROOM_PAGE("go_to_create_room_page", EnumSet.of(ADMIN)),
    GO_TO_DISCOUNT_MANAGEMENT_PAGE("go_to_discount_management_page", EnumSet.of(ADMIN)),
    GO_TO_HOME_PAGE("go_to_home_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_MAIN_PAGE("go_to_main_page", EnumSet.of(GUEST, CLIENT, ADMIN)),
    GO_TO_ORDER_MANAGEMENT_PAGE("go_to_order_management_page", EnumSet.of(ADMIN)),
    GO_TO_ORDER_PAGE("go_to_order_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_REMOVE_DISCOUNT_PAGE("go_to_remove_discount_page", EnumSet.of(ADMIN)),
    GO_TO_REPLENISH_BALANCE_PAGE("go_to_replenish_balance_page", EnumSet.of(CLIENT)),
    GO_TO_REVIEW_MANAGEMENT_PAGE("go_to_review_management_page", EnumSet.of(ADMIN)),
    GO_TO_ROOM_MANAGEMENT_PAGE("go_to_room_management_page", EnumSet.of(ADMIN)),
    GO_TO_SING_IN_PAGE("go_to_sing_in_page", EnumSet.of(GUEST)),
    GO_TO_UPDATE_DISCOUNT_PAGE("go_to_update_discount_page", EnumSet.of(CLIENT, ADMIN)),
    GO_TO_UPDATE_ORDER_PAGE("go_to_update_order_page", EnumSet.of(ADMIN)),
    GO_TO_UPDATE_REVIEW_PAGE("go_to_update_review_page", EnumSet.of(ADMIN)),
    GO_TO_UPDATE_ROOM_PAGE("go_to_update_room_page", EnumSet.of(ADMIN)),
    GO_TO_USER_MANAGEMENT_PAGE("go_to_user_management_page", EnumSet.of(ADMIN)),
    REMOVE_DISCOUNT("remove_discount", EnumSet.of(ADMIN)),
    REPLENISH_BALANCE("replenish_balance", EnumSet.of(CLIENT)),
    SING_IN("sing_in", EnumSet.of(GUEST)),
    SING_OUT("sing_out", EnumSet.of(CLIENT, ADMIN)),
    UPDATE_DESCRIPTION("update_description", EnumSet.of(ADMIN)),
    UPDATE_DISCOUNT("update_discount", EnumSet.of(ADMIN)),
    UPDATE_ORDER("update_order", EnumSet.of(ADMIN)),
    UPDATE_PERSONAL_DATA("update_personal_data", EnumSet.of(CLIENT, ADMIN)),
    UPDATE_PREVIEW("update_preview", EnumSet.of(ADMIN)),
    UPDATE_REVIEW("update_review", EnumSet.of(ADMIN)),
    UPDATE_ROOM("update_room", EnumSet.of(ADMIN)),
    UPLOAD_IMAGE("upload_image", EnumSet.of(ADMIN));

    private String webCommandName;
    EnumSet<User.Role> acceptableRole;

    private CommandName(String webCommandName, EnumSet<User.Role> acceptableRole) {
        this.webCommandName = webCommandName;
        this.acceptableRole = acceptableRole;
    }

    public EnumSet<User.Role> getAcceptableRole() {
        return acceptableRole;
    }
}
