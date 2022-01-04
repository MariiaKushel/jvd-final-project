package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.validator.UserValidator;

public final class UserValidatorImpl implements UserValidator {

    private static final String EMAIL_REGEX =
            "[\\d\\p{Alpha}]([\\d\\p{Alpha}_\\-\\.]*)[\\d\\p{Alpha}_\\-]@[\\d\\p{Alpha}_\\-]{2,}\\.\\p{Lower}{2,6}";
    private static final String PASSWORD_REGEX =
            "[\\d\\p{Alpha}\\p{Punct}]+";
    private static final String NAME_REGEX =
            "([\\p{Alpha}[а-яА-яёЁ]])+(\\s([\\p{Alpha}[а-яА-яёЁ]]))*";
    private static final String PHONE_NUMBER_REGEX =
            "\\+375(29|44|17|25|33)\\d{7}";

    private static final UserValidatorImpl instance = new UserValidatorImpl();

    private UserValidatorImpl() {

    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateEmail(String email) {
        if (email.length() > 50) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean validatePassword(String password) {
        if (password.length() < 4 || password.length() > 12) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean validateName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 13) {
            return false;
        }
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    @Override
    public boolean validate(User user, String password) {
        String email = user.getEmail();
        String name = user.getName();
        String phoneNumber = user.getPhoneNumber();
        return validateEmail(email) && validatePassword(password)
                && validateName(name) && validatePhoneNumber(phoneNumber);
    }
}
