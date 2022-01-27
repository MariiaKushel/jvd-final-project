package by.javacourse.hotel.validator;

import java.math.BigDecimal;
import java.util.Map;

public interface ReviewValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateDateRange (String from, String to);

    boolean validateContent (String content);

}
