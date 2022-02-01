package by.javacourse.hotel.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * {@code PasswordEncryptor} util class to help encrypt password
 */
public class PasswordEncryptor {

    private static final String SALT = "4d!5&T#";

    /**
     * {@code encode} method to encrypt password use {@link org.apache.commons.codec.digest.DigestUtils}
     * @param password - password before encrypt
     * @return encrypted password
     */
    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password + SALT);
    }
}
