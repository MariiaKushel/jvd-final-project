package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.RoomValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.WRONG_NUMBER_SES;

public final class RoomValidatorImpl implements RoomValidator {

    private static final String DEFAULT_MIN_PRICE = "0";
    private static final String DEFAULT_MAX_PRICE = "9999999.99";

    private static final String DEFAULT_MIN_RATING = "0";
    private static final String DEFAULT_MAX_RATING = "5.00";

    private static final int DEFAULT_MAX_NUMBER = 99999;

    private static final RoomValidatorImpl instance = new RoomValidatorImpl();

    private RoomValidatorImpl() {

    }

    public static RoomValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateDateRange(String dateFrom, String dateTo) {
        boolean isValid = false;
        if (dateFrom.isEmpty() || dateTo.isEmpty()) {
            return isValid;
        }
        try {
            LocalDate from = LocalDate.parse(dateFrom);
            LocalDate to = LocalDate.parse(dateTo);
            isValid = from.compareTo(to) < 0;
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validatePriceRange(String priceFrom, String priceTo) {
        boolean isValid = false;
        try {
            BigDecimal from = new BigDecimal(priceFrom);
            BigDecimal to = new BigDecimal(priceTo);
            isValid = from.compareTo(new BigDecimal(0)) >= 0 && from.compareTo(to) < 0;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }


    @Override
    public boolean validateRating(String ratingFrom, String ratingTo) {
        boolean isValid = false;
        if (ratingFrom.isEmpty() && ratingTo.isEmpty()) {
            isValid = true;
            return isValid;
        }
        if (ratingFrom.isEmpty()) {
            ratingFrom = DEFAULT_MIN_RATING;
        }
        if (ratingTo.isEmpty()) {
            ratingTo = DEFAULT_MAX_RATING;
        }
        try {
            BigDecimal from = new BigDecimal(ratingFrom);
            BigDecimal to = new BigDecimal(ratingTo);
            isValid = from.compareTo(new BigDecimal(0)) >= 0 && from.compareTo(to) < 0;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateNumber(String number) {
        boolean isValid = false;
        if (number.isEmpty()) {
            isValid = true;
            return isValid;
        }
        try {
            int num = Integer.parseInt(number);
            isValid = num > 0 && num <= DEFAULT_MAX_NUMBER;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validatePrice(String price) {
        boolean isValid = true;
        try {
            BigDecimal priceB = new BigDecimal(price);
            isValid = priceB.compareTo(BigDecimal.ZERO) >= 0 && priceB.compareTo(new BigDecimal(DEFAULT_MAX_PRICE)) < 0;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateVisible(String visible) {
        boolean isValid = false;
        if (visible.isEmpty()) {
            isValid = true;
            return isValid;
        }
        isValid = Boolean.parseBoolean(visible);
        return isValid;
    }

    public boolean validateSearchParameter(Map<String, String> searchParameter) {
        boolean isValid = true;

        String dateFrom = searchParameter.get(DATE_FROM_ATR);
        String dateTo = searchParameter.get(DATE_TO_ATR);
        String priceFrom = searchParameter.get(PRICE_FROM_ATR);
        String priceTo = searchParameter.get(PRICE_TO_ATR);

        isValid = validateDateRange(dateFrom, dateTo) && validatePriceRange(priceFrom, priceTo);
        return isValid;
    }

    public boolean validateSearchParameterAdmin(Map<String, String> searchParameter) {
        boolean isValid = true;
        String number = searchParameter.get(ROOM_NUMBER_ATR);
        String priceFrom = searchParameter.get(PRICE_FROM_ATR);
        String priceTo = searchParameter.get(PRICE_TO_ATR);
        String ratingFrom = searchParameter.get(RATING_FROM_ATR);
        String ratingTo = searchParameter.get(RATING_TO_ATR);

        if (!validateNumber(number)) {
            searchParameter.put(WRONG_NUMBER_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validatePriceRange(priceFrom, priceTo)) {
            searchParameter.put(WRONG_PRICE_RANGE_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validateRating(ratingFrom, ratingTo)) {
            searchParameter.put(WRONG_RATING_RANGE_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateRoomData(Map<String, String> roomData) {
        boolean isValid = true;
        String tempNumber = roomData.get(ROOM_NUMBER_SES);
        String tempSleepingPlace = roomData.get(SLEEPING_PLACE_SES);
        String tempPrice = roomData.get(PRICE_SES);
        RoomValidator validator = RoomValidatorImpl.getInstance();
        if (tempNumber.isEmpty() || !validator.validateNumber(tempNumber)) {
            roomData.put(WRONG_NUMBER_SES, RoomValidator.WRONG_DATA_MARKER);
            isValid = false;
        }
        if (tempSleepingPlace.isEmpty() || !validator.validateNumber(tempSleepingPlace)) {
            roomData.put(WRONG_SLEEPING_PLACE_SES, RoomValidator.WRONG_DATA_MARKER);
            isValid = false;
        }
        if (tempPrice.isEmpty() || !validator.validatePrice(tempPrice)) {
            roomData.put(WRONG_PRICE_SES, RoomValidator.WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }
}
