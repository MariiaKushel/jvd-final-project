package by.javacourse.hotel.validator;

public interface DiscountValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateRate(String rate);

}
