package test.javacourse.hotel.validator;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.validator.RoomOrderValidator;
import by.javacourse.hotel.validator.impl.RoomOrderValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static org.testng.Assert.*;

public class RoomOrderValidatorImplTest {

    private RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();

    @DataProvider(name = "orderDataProvider")
    public Object[][] createData() {
        Map<String, String> map1 = new HashMap<>();
        map1.put(PREPAYMENT_SES, "true");
        map1.put(TOTAL_AMOUNT_SES, "1000");
        BigDecimal balance1 = new BigDecimal(5000);

        Map<String, String> map2 = new HashMap<>();
        map2.put(PREPAYMENT_SES, "true");
        map2.put(TOTAL_AMOUNT_SES, "1000");
        BigDecimal balance2 = new BigDecimal(20);

        Map<String, String> map3 = new HashMap<>();
        map3.put(PREPAYMENT_SES, "false");
        map3.put(TOTAL_AMOUNT_SES, "1000");
        BigDecimal balance3 = new BigDecimal(5000);

        return new Object[][]{
                {map1, balance1, true},
                {map2, balance2, false},
                {map3, balance3, true},
        };
    }

    @Test(dataProvider = "orderDataProvider")
    public void testValidateOrderData(Map<String, String> map, BigDecimal balance, boolean expected) {
        boolean actual = validator.validateOrderData(map, balance);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "dateRangeProvider")
    public Object[][] createData1() {
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

    @DataProvider(name = "lastProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {"", true},
                {"10", true},
                {"azaza", false},
                {"-5", false},
                {"99999999999", false},
        };
    }

    @Test(dataProvider = "lastProvider")
    public void testValidateLast(String last, boolean expected) {
        boolean actual = validator.validateLast(last);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "statusProvider")
    public Object[][] createData3() {
        return new Object[][]{
                {"CLIENT", RoomOrder.Status.NEW, RoomOrder.Status.CANCELED_BY_CLIENT, true},
                {"CLIENT", RoomOrder.Status.CONFIRMED, RoomOrder.Status.CANCELED_BY_CLIENT, false},
                {"ADMIN", RoomOrder.Status.NEW, RoomOrder.Status.IN_PROGRESS, false},
                {"ADMIN", RoomOrder.Status.CONFIRMED, RoomOrder.Status.NEW, false},
                {"ADMIN", RoomOrder.Status.CONFIRMED, RoomOrder.Status.IN_PROGRESS, true},
        };
    }

    @Test(dataProvider = "statusProvider")
    public void testValidateStatus(String role, RoomOrder.Status oldStatus, RoomOrder.Status newStatus, boolean expected) {
        boolean actual = validator.validateStatus(role, oldStatus, newStatus);
        Assert.assertEquals(actual, expected);
    }
}