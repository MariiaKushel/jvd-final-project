package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.DiscountValidator;

public final class DiscountValidatorImpl implements DiscountValidator {

    private static final int DEFAULT_MAX_RATE = 99999;

    private static final DiscountValidatorImpl instance = new DiscountValidatorImpl();

    private DiscountValidatorImpl() {

    }

    public static DiscountValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateRate(String rate) {
        boolean isValid = false;
        if (rate.isEmpty()) {
            isValid = true;
            return isValid;
        }
        try {
            int num = Integer.parseInt(rate);
            isValid = num > 0 && num <= DEFAULT_MAX_RATE;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }
}
