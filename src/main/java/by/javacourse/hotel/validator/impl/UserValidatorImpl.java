package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.UserValidator;

import java.util.Map;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

/**
 * {@code UserValidatorImpl} class implements functional of {@link UserValidator}
 */
public final class UserValidatorImpl implements UserValidator {

    private static final String EMAIL_REGEX =
            "[\\d\\p{Lower}]([\\d\\p{Lower}_\\-\\.]*)[\\d\\p{Lower}_\\-]@[\\d\\p{Lower}_\\-]{2,}\\.\\p{Lower}{2,6}";
    private static final String PART_OF_EMAIL_REGEX =
            "[\\d\\p{Lower}_\\-\\.@]+";
    private static final String PASSWORD_REGEX =
            "[\\p{Graph}&&[^\\<\\>]]+";
    private static final String NAME_REGEX =
            "[\\wа-яА-яёЁ][\\wа-яА-яёЁ\\s]*";
    private static final String PHONE_NUMBER_REGEX =
            "\\+375(29|44|17|25|33)\\d{7}";
    private static final String PART_OF_PHONE_NUMBER_REGEX =
            "[\\+\\d]{1,12}";
    private static final String AMOUNT_REGEX = "\\d{1,7}(\\.\\d\\d)??";
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
    public boolean validatePartOfEmail(String emailPart) {
        return emailPart.matches(PART_OF_EMAIL_REGEX);
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
    public boolean validatePartOfPhoneNumber(String phoneNumberPart) {
        return phoneNumberPart.matches(PART_OF_PHONE_NUMBER_REGEX);
    }


    @Override
    public boolean validateUserDataCreate(Map<String, String> userData) {
        String email = userData.get(EMAIL_SES);
        String name = userData.get(NAME_SES);
        String phoneNumber = userData.get(PHONE_NUMBER_SES);
        String password = userData.get(PASSWORD_SES);
        String repeatPassword = userData.get(REPEAT_PASSWORD_SES);

        boolean isValid = true;
        if (!password.equals(repeatPassword)) {
            userData.put(WRONG_MISMATCH_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validateEmail(email)) {
            userData.put(WRONG_EMAIL_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validatePassword(password)) {
            userData.put(WRONG_PASSWORD_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validateName(name)) {
            userData.put(WRONG_NAME_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validatePhoneNumber(phoneNumber)) {
            userData.put(WRONG_PHONE_NUMBER_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateUserDataUpdate(Map<String, String> userData) {
        String name = userData.get(NAME_SES);
        String phoneNumber = userData.get(PHONE_NUMBER_SES);
        String password = userData.get(PASSWORD_SES);

        boolean isValid = true;
        if (password != null && !validatePassword(password)) {
            userData.put(WRONG_PASSWORD_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validateName(name)) {
            userData.put(WRONG_NAME_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!validatePhoneNumber(phoneNumber)) {
            userData.put(WRONG_PHONE_NUMBER_SES, WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateUserSearchParameters(Map<String, String> parameters) {
        String partOfEmail = parameters.get(EMAIL_ATR);
        String partOfPhoneNumber = parameters.get(PHONE_NUMBER_ATR);
        String name = parameters.get(NAME_ATR);

        boolean isValid = true;
        if (!partOfEmail.isEmpty() && !validatePartOfEmail(partOfEmail)) {
            parameters.put(WRONG_EMAIL_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!partOfPhoneNumber.isEmpty() && !validatePartOfPhoneNumber(partOfPhoneNumber)) {
            parameters.put(WRONG_PHONE_NUMBER_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!name.isEmpty() && !validateName(name)) {
            parameters.put(WRONG_NAME_ATR, WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateAmount(String amount) {
        return amount.matches(AMOUNT_REGEX);
    }
}
