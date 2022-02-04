package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.RoomValidator;
import by.javacourse.hotel.validator.impl.RoomValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RoomValidatorImplTest {

    private RoomValidator validator = RoomValidatorImpl.getInstance();

    @DataProvider(name = "dateRangeProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"2022-01-01", "2022-01-05", true},
                {"2022-01-05", "2022-01-01", false},
                {"2022-01-01", "azaza", false},
                {"azaza", "2022-01-05", false},
                {"", "", false},
        };
    }

    @Test(dataProvider = "dateRangeProvider")
    public void testValidateDateRange(String from, String to, boolean expected) {
        boolean actual = validator.validateDateRange(from, to);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "priceRangeProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {"90", "120", true},
                {"120", "90", false},
                {"90", "azaza", false},
                {"azaza", "120", false},
                {"", "", false},
                {"-50", "120", false},
        };
    }

    @Test(dataProvider = "priceRangeProvider")
    public void testValidatePriceRange(String from, String to, boolean expected) {
        boolean actual = validator.validatePriceRange(from, to);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "numberProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"", true},
                {"10", true},
                {"azaza", false},
                {"-5", false},
                {"99999999999", false},
        };
    }

    @Test(dataProvider = "numberProvider")
    public void testValidateNumber(String number, boolean expected) {
        boolean actual = validator.validateNumber(number);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "priceProvider")
    public Object[][] createData4() {
        return new Object[][]{
                {"10", true},
                {"azaza", false},
                {"-5", false},
                {"99999999999", false},
        };
    }

    @Test(dataProvider = "priceProvider")
    public void testValidatePrice(String price, boolean expected) {
        boolean actual = validator.validatePrice(price);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "visibleProvider")
    public Object[][] createData5() {
        return new Object[][]{
                {"", true},
                {"true", true},
                {"false", false},
                {"azaza", false},
        };
    }

    @Test(dataProvider = "visibleProvider")
    public void testValidateVisible(String visible, boolean expected) {
        boolean actual = validator.validateVisible(visible);
        Assert.assertEquals(actual, expected);
    }
}