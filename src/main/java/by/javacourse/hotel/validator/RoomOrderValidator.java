package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface RoomOrderValidator {

    boolean validateOrderData(Map<String, String> orderData, BigDecimal balance);

}
