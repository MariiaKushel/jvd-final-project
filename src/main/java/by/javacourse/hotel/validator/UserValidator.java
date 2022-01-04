package by.javacourse.hotel.validator;

import by.javacourse.hotel.entity.User;

public interface UserValidator {

    boolean validateEmail(String email);

    boolean validatePassword(String password);

    boolean validateName(String name);

    boolean validatePhoneNumber(String phoneNumber);

    boolean validate(User user, String password);

}
