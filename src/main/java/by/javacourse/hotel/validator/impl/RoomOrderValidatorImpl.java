package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.RoomOrderValidator;

import java.math.BigDecimal;
import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestParameter.PREPAYMENT;
import static by.javacourse.hotel.controller.command.RequestParameter.TOTAL_AMOUNT;

public class RoomOrderValidatorImpl implements RoomOrderValidator {
    private static final RoomOrderValidatorImpl instance = new RoomOrderValidatorImpl();

    private RoomOrderValidatorImpl() {

    }

    public static RoomOrderValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateOrderData(Map<String, String> orderData, BigDecimal balance) {
        boolean isValid = true;
        boolean isPrerayment = Boolean.parseBoolean(orderData.get(PREPAYMENT));
        if (isPrerayment) {
            BigDecimal totalAmount = new BigDecimal(orderData.get(TOTAL_AMOUNT));
            isValid = balance.compareTo(totalAmount) >= 0 ? true: false;
        }
        return isValid;
    }
}
