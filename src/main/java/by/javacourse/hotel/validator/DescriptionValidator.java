package by.javacourse.hotel.validator;

import java.util.Map;

/**
 * {@code DescriptionValidator} interface represent functional to validate input data
 * for work with class {@link by.javacourse.hotel.entity.Description}
 */
public interface DescriptionValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code validateDescriptionData} method to validate description data for create description
     * @param descriptionData - map consist description data
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateDescriptionData(Map<String, String> descriptionData);

}
