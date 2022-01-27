package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface RoomOrderValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateOrderData(Map<String, String> orderData, BigDecimal balance);

    boolean validateDateRange (String from, String to);

    boolean validateLast(String last);

    boolean validateStatus(String role, RoomOrder.Status oldStatus, RoomOrder.Status newStatus);

}
