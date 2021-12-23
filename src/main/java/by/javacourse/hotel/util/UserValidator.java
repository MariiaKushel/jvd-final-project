package by.javacourse.hotel.util;

import by.javacourse.hotel.model.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserValidator { //TODO final class + private constructor + static methods ok? or singltone?

    private static final String LOGIN_REGEX =
            "[\\d\\p{Alpha}]([\\d\\p{Alpha}_\\-\\.]*)[\\d\\p{Alpha}_\\-]@[\\d\\p{Alpha}_\\-]{2,}\\.\\p{Lower}{2,6}";
    private static final String PASSWORD_REGEX =
            "[\\d\\p{Alpha}\\p{Punct}]+";
    private static final String NAME_REGEX =
            "([\\p{Alpha}[а-яА-яёЁ]])+(\\s([\\p{Alpha}[а-яА-яёЁ]]))*";
    private static final String PHONE_NUMBER_REGEX =
            "\\+375(29|44|17|25|33)\\d{7}";

    private UserValidator() {

    }

    public static boolean validateLogin(String login) {
        if (login.length() > 50) {
            return false;
        }
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 4 || password.length() > 12) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 13) {
            return false;
        }
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean validate(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String name = user.getName();
        String phoneNumber = user.getPhoneNumber();
        return validateLogin(login) && validatePassword(password)
                && validateName(name) && validatePhoneNumber(phoneNumber);
    }
}
