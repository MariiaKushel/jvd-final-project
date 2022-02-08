package by.javacourse.hotel.model.dao;

/**
 * {@code ColumnName} class represent container for column name in database
 * The class does not contain functionality, only constants.
 */
public final class ColumnName {

    //table hotel.users
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_STATUS = "status";
    public static final String BALANCE = "balance";

    //table hotel.discounts
    public static final String DISCOUNT_ID = "discount_id";
    public static final String RATE = "rate";

    //table hotel.rooms
    public static final String ROOM_ID = "room_id";
    public static final String NUMBER = "number";
    public static final String SLEEPING_PLACE = "sleeping_place";
    public static final String PRICE_PER_DAY = "price_per_day";
    public static final String RATING = "rating";
    public static final String VISIBLE = "visible";
    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";

    //table hotel.images
    public static final String IMAGE_ID = "image_id";
    public static final String IMAGE = "image";
    public static final String PREVIEW = "preview";

    //table hotel.descriptions
    public static final String DESCRIPTION_ID = "description_id";
    public static final String DESCRIPTION_RU = "description_ru";
    public static final String DESCRIPTION_EN = "description_en";

    //table hotel.reviews
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_DATE = "date";
    public static final String ROOM_MARK = "room_mark";
    public static final String REVIEW_CONTENT = "review";
    public static final String HIDDEN = "hidden";

    //table hotel.room_orders
    public static final String ROOM_ORDER_ID ="room_order_id";
    public static final String ROOM_ORDER_DATE ="date";
    public static final String FROM ="from";
    public static final String TO ="to";
    public static final String AMOUNT ="amount";
    public static final String ROOM_ORDER_STATUS ="status";
    public static final String PREPAYMENT ="prepayment";

    public static final String NUM ="num";

    private ColumnName() {
    }
}
