package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.RoomOrder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * {@code RoomOrderValidator} interface represent functional to validate input data
 * for work with class {@link by.javacourse.hotel.entity.RoomOrder}
 */
public interface RoomOrderValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateOrderData} method to validate order data to create new order
     * @param orderData - order date to create
     * @param balance - user balance
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateOrderData(Map<String, String> orderData, BigDecimal balance);

    /**
     * {@code validateDateRange} method to validate date range for order search
     * @param from - low border of range
     * @param to - upper border of range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDateRange (String from, String to);

    /**
     * {@code validateLast} method to validate last value for order search
     * @param last - number of last orders for search
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateLast(String last);

    /**
     * {@code validateStatus} method to validate role, old and new status for change order status
     * @param role - user role
     * @param oldStatus - old status of order
     * @param newStatus - new status of order
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateStatus(String role, RoomOrder.Status oldStatus, RoomOrder.Status newStatus);

}
