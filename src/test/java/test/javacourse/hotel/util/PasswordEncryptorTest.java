package test.javacourse.hotel.util;

import by.javacourse.hotel.util.PasswordEncryptor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordEncryptorTest {

    @Test
    public void testEncryptPositive() {
        String password1 = "Asa58!te2*";
        String password2 = "Asa58!te2*";
        String hash1 = PasswordEncryptor.encrypt(password1);
        String hash2 = PasswordEncryptor.encrypt(password2);
        Assert.assertTrue(hash1.equals(hash2));
    }

    @Test
    public void testEncryptNegative() {
        String password1 = "Asa58!te2*";
        String password2 = "Nd891!!56q";
        String hash1 = PasswordEncryptor.encrypt(password1);
        String hash2 = PasswordEncryptor.encrypt(password2);
        Assert.assertFalse(hash1.equals(hash2));
    }
}
