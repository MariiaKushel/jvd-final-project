package by.javacourse.hotel.validator;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RoomSearchParameterValidator {

    boolean validateDate(LocalDate dateFrom, LocalDate dateTo);

    boolean validatePrice(BigDecimal priceFrom, BigDecimal priceTo);

}
