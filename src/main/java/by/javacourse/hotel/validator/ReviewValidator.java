package by.javacourse.hotel.validator;

/**
 * {@code ReviewValidator} interface represent functional to validate input data
 * for work with class {@link by.javacourse.hotel.entity.Review}
 */
public interface ReviewValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateDateRange} method to validate date range for review search
     * @param from - low border of range
     * @param to - upper border of range
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDateRange (String from, String to);

    /**
     * {@code validateContent} method to validate date content for create review
     * @param content - text content
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateContent (String content);

}
