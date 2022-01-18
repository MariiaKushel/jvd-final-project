package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.validator.RoomSearchParameterValidator;
import by.javacourse.hotel.validator.UserValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class RoomSearchParameterValidatorImpl implements RoomSearchParameterValidator {


    private static final RoomSearchParameterValidatorImpl instance = new RoomSearchParameterValidatorImpl();

    private RoomSearchParameterValidatorImpl() {

    }

    public static RoomSearchParameterValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateDate(LocalDate dateFrom, LocalDate dateTo) {
        boolean isValid = false;
        isValid = dateFrom.compareTo(dateTo) < 0 ? true : false;
        return isValid;
    }

    @Override
    public boolean validatePrice(BigDecimal priceFrom, BigDecimal priceTo) {
        boolean isValid = false;
        isValid = priceFrom.compareTo(priceTo) > 0 ? false : true;
        return isValid;
    }
}
