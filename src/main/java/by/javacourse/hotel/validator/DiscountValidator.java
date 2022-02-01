package by.javacourse.hotel.validator;

/**
 * {@code DiscountValidator} interface represent functional to validate input data
 * for work with class {@link  by.javacourse.hotel.entity.Discount}
 */
public interface DiscountValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateRate} method to validate date range for create discount
     * @param rate - value of discount range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateRate(String rate);

}
