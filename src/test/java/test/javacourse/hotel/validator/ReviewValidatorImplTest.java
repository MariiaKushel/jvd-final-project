package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.ReviewValidator;
import by.javacourse.hotel.validator.impl.ReviewValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReviewValidatorImplTest {

    private ReviewValidator validator = ReviewValidatorImpl.getInstance();

    @DataProvider(name = "dateRangeProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"2022-01-01", "2022-01-05", true},
                {"2022-01-01", "2022-01-01", true},
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

    @DataProvider(name = "contentProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {"azaza", true},
                {"урууру", true},
                {"<aaaaa>", false},
                {"", false},
        };
    }

    @Test(dataProvider = "contentProvider")
    public void testValidateContent(String content, boolean expected) {
        boolean actual = validator.validateContent(content);
        Assert.assertEquals(actual, expected);
    }
}