package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.UserValidator;
import by.javacourse.hotel.validator.impl.UserValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorImplTest {

    private UserValidator validator = UserValidatorImpl.getInstance();

    @DataProvider(name = "emailProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"ivanov@gmail.com", true},
                {"ivanov-33.5-rr@gmail.com", true},
                {"ivanov", false},
                {"ivanov.@gmail.com", false},
                {"ivanov@gmail.com.com", false},
        };
    }
    @Test(dataProvider = "emailProvider")
    public void testValidateEmail(String email, boolean expected) {
        boolean actual = validator.validateEmail(email);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "partOfEmailProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {"ivanov@gmail.com", true},
                {"@", true},
                {"-33", true},
                {"!!!", false},
                {"<///>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
        };
    }
    @Test(dataProvider = "partOfEmailProvider")
    public void testValidatePartOfEmail(String partOfEmail, boolean expected) {
        boolean actual = validator.validatePartOfEmail(partOfEmail);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "passwordProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {"ivanov123", true},
                {"1234", true},
                {"123", false},
                {"<///>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
        };
    }
    @Test(dataProvider = "passwordProvider")
    public void testValidatePassword(String password, boolean expected) {
        boolean actual = validator.validatePassword(password);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "nameProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"Ivanov", true},
                {"Petr Swonson", true},
                {"Ivan!!!", false},
                {"УРУРУ", true},
                {"<УРУРУ>", false},
                {"11111111111111111111111111111111111111111111111111111111", false},
                {"", false},
        };
    }
    @Test(dataProvider = "nameProvider")
    public void testValidateName(String name, boolean expected) {
        boolean actual = validator.validateName(name);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "phoneNumberProvider")
    public Object[][] createData4() {
        return new Object[][]{
                {"+375291111111", true},
                {"+372291111", false},
                {"+3752911111111111", false},
                {"+375991111111", false},
                {"<УРУРУ>", false},
                {"", false},
        };
    }
    @Test(dataProvider = "phoneNumberProvider")
    public void testValidatePhoneNumber(String phoneNumber, boolean expected) {
        boolean actual = validator.validatePhoneNumber(phoneNumber);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "partOfPhoneNumberProvider")
    public Object[][] createData5() {
        return new Object[][]{
                {"111", true},
                {"375281111111", true},
                {"3752911111111111", false},
                {"<УРУРУ>", false},
                {"", false},
        };
    }
    @Test(dataProvider = "partOfPhoneNumberProvider")
    public void testValidatePartOfPhoneNumber(String partOfPhoneNumber, boolean expected) {
        boolean actual = validator.validatePartOfPhoneNumber(partOfPhoneNumber);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "amountProvider")
    public Object[][] createData6() {
        return new Object[][]{
                {"153.37", true},
                {"-153.37", false},
                {"153.3333", false},
                {"15333333333", false},
                {"<УРУРУ>", false},
                {"", false},
        };
    }
    @Test(dataProvider = "amountProvider")
    public void testValidateAmount(String amount, boolean expected) {
        boolean actual = validator.validateAmount(amount);
        Assert.assertEquals(actual, expected);
    }
}