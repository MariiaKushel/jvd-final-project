package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.User;

import java.util.Map;

public interface UserValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateEmail(String email);

    boolean validatePassword(String password);

    boolean validateName(String name);

    boolean validatePhoneNumber(String phoneNumber);

    boolean validate(User user, String password);

    boolean validateUserDataCreate(Map<String, String> userData);

    boolean validateUserDataUpdate(Map<String, String> userData);

}
