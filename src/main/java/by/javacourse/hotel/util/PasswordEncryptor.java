package by.javacourse.hotel.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryptor {
    private static final String SALT = "4d!5&T#";

    public static String encrypt(String password) {

        return DigestUtils.md5Hex(password + SALT);
    }
}
