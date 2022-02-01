package by.javacourse.hotel.validator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * {@code UserValidator} interface represent functional to validate input data
 * for work with class {@link by.javacourse.hotel.entity.User}
 */
public interface UserValidator {

    /**
     * {@code WRONG_DATA_MARKER} constant represent string to mark wrong data
     */
    String WRONG_DATA_MARKER = "Wrong data";

    /**
     * {@code MAX_BALANCE} constant represent max balance value
     */
    BigDecimal MAX_BALANCE = new BigDecimal("9999999.99");

    /**
     * {@code validateEmail} method to validate user email
     * @param email - user email
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateEmail(String email);

    /**
     * {@code validateEmail} method to validate part of email
     * @param emailPart - part of email
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePartOfEmail(String emailPart);

    /**
     * {@code validateEmail} method to validate user password
     * @param password - user password
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePassword(String password);

    /**
     * {@code validateEmail} method to validate username
     * @param name - username
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateName(String name);

    /**
     * {@code validateEmail} method to validate user phone number
     * @param phoneNumber - user phone number
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePhoneNumber(String phoneNumber);

    /**
     * {@code validateEmail} method to validate user part of phone number
     * @param phoneNumberPart -  part of phone number
     * @return result of validation, true - valid, false - not valid
     */
    boolean validatePartOfPhoneNumber(String phoneNumberPart);

    /**
     * {@code validateEmail} method to validate user data to create new user
     * @param userData - user data to create new user
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateUserDataCreate(Map<String, String> userData);

    /**
     * {@code validateEmail} method to validate user data to update new user
     * @param userData - user data to update new user
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateUserDataUpdate(Map<String, String> userData);

    /**
     * {@code validateEmail} method to validate search parameters to find user
     * @param parameters - search parameters to find user
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateUserSearchParameters(Map<String, String> parameters);

    /**
     * {@code validateEmail} method to validate amount
     * @param amount - amount as string
     * @return result of validation, true - valid, false - not valid
     */
    boolean validateAmount(String amount);

}
