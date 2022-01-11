package by.javacourse.hotel.model.dao;

public final class ColumnName {
    //table hotel.users
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_STATUS = "status";
    public static final String DISCOUNT_ID = "discount_id";
    public static final String BALANCE = "balance";

    //table hotel.discounts
    public static final String RATE = "rate";

    //table.hotel.rooms
    public static final String ROOM_ID = "room_id";
    public static final String NUMBER = "number";
    public static final String SLEEPING_PLACE = "sleeping_place";
    public static final String PRICE_PER_DAY = "price_per_day";
    public static final String RATING = "rating";
    public static final String VISIBLE = "visible";
    public static final String DESCRIPTION = "description";

    //table.hotel.images
    public static final String IMAGE_ID = "image_id";
    public static final String IMAGE = "image";
    public static final String PREVIEW = "preview";

    private ColumnName(){
    }
}
