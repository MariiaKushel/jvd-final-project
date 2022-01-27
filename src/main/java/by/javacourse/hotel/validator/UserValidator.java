package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface UserValidator {
    String WRONG_DATA_MARKER = "Wrong data";
    BigDecimal MAX_BALANCE = new BigDecimal("9999999.99");

    boolean validateEmail(String email);
    boolean validatePartOfEmail(String email);

    boolean validatePassword(String password);

    boolean validateName(String name);

    boolean validatePhoneNumber(String phoneNumber);
    boolean validatePartOfPhoneNumber(String partOfPhoneNumber);

    boolean validate(User user, String password);

    boolean validateUserDataCreate(Map<String, String> userData);

    boolean validateUserDataUpdate(Map<String, String> userData);

    boolean validateSearchParameter(Map<String, String> searchParameter);

    boolean validateAmount(String amount);

}
