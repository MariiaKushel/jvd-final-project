package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.ReviewValidator;
import by.javacourse.hotel.validator.RoomOrderValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * {@code ReviewValidatorImpl} class implements functional of {@link ReviewValidator}
 */
public class ReviewValidatorImpl implements ReviewValidator {

    private static final String CONTENT_REGEX = "[^><]+";

    private static final ReviewValidatorImpl instance = new ReviewValidatorImpl();

    private ReviewValidatorImpl() {

    }

    public static ReviewValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateDateRange(String dateFrom, String dateTo) {
        boolean isValid = false;
        try {
            LocalDate from = LocalDate.parse(dateFrom);
            LocalDate to = LocalDate.parse(dateTo);
            isValid = from.compareTo(to) <= 0;
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateContent(String content) {
        return content.matches(CONTENT_REGEX);
    }
}
