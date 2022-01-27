package by.javacourse.hotel.validator;

import java.util.Map;

public interface RoomValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateDateRange(String dateFrom, String dateTo);

    boolean validatePriceRange(String priceFrom, String priceTo);

    boolean validateRating(String ratingFrom, String ratingTo);

    boolean validateNumber(String number);

    boolean validateVisible(String visible);

    boolean validateSearchParameter(Map<String, String> searchParameter);

    boolean validateSearchParameterAdmin(Map<String, String> searchParameter);

}
