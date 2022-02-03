package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.DiscountValidator;
import by.javacourse.hotel.validator.impl.DiscountValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DiscountValidatorImplTest {

    private DiscountValidator validator = DiscountValidatorImpl.getInstance();

    @DataProvider(name = "rateProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"", false},
                {"10", true},
                {"azaza", false},
                {"-5", false},
                {"99999999999", false},
        };
    }

    @Test(dataProvider = "rateProvider")
    public void testValidateRate(String rate, boolean expected) {
        boolean actual = validator.validateRate(rate);
        Assert.assertEquals(actual, expected);
    }
}