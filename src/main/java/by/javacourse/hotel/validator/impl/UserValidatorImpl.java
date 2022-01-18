package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.validator.UserValidator;

public final class UserValidatorImpl implements UserValidator {

    private static final String EMAIL_REGEX =
            "[\\d\\p{Lower}]([\\d\\p{Lower}_\\-\\.]*)[\\d\\p{Lower}_\\-]@[\\d\\p{Lower}_\\-]{2,}\\.\\p{Lower}{2,6}";
    private static final String PASSWORD_REGEX =
            "[\\p{Graph}]+";
    private static final String NAME_REGEX =
            "[\\wа-яА-яёЁ][\\wа-яА-яёЁ\\s]*";
    private static final String PHONE_NUMBER_REGEX =
            "\\+375(29|44|17|25|33)\\d{7}";
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 12;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int PHONE_NUMBER_LENGTH = 13;

    private static final UserValidatorImpl instance = new UserValidatorImpl();

    private UserValidatorImpl() {

    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateEmail(String email) {
        if (email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean validatePassword(String password) {
        if (password.length() > MAX_PASSWORD_LENGTH || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            return false;
        }
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
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
