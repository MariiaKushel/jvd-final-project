package by.javacourse.hotel.validator;

import java.util.Map;

/**
 * {@code RoomValidator} interface represent functional to validate input data
 * for work with class {@link by.javacourse.hotel.entity.Room}
 */
public interface RoomValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";
    String PREVIOUS_SHEET = "-1";
    String NEXT_SHEET = "1";

    /**
     * {@code validateDateRange} method to validate date range for room search
     * @param from - low border of range
     * @param to - upper border of range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDateRange(String from, String to);

    /**
     * {@code validatePriceRange} method to validate price range for room search
     * @param from - low border of range
     * @param to - upper border of range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePriceRange(String from, String to);

    /**
     * {@code validatePrice} method to validate room price
     * @param price - price value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePrice(String price);

    /**
     * {@code validateNumber} method to validate room number
     * @param number - number value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateNumber(String number);

    /**
     * {@code validateVisible} method to validate room visible
     * @param visible - visible value as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateVisible(String visible);

    /**
     * {@code validateRoomData} method to validate room data to create or update room
     * @param roomData - room data to create or update room
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateRoomData(Map<String, String> roomData);

    /**
     * {@code validateDirection} method to validate direction to flip sheets
     * @param direction - "1"-next, "-1"-previous
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDirection(String direction);
}
