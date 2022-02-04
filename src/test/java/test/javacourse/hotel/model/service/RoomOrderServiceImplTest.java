package test.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.impl.RoomOrderServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class RoomOrderServiceImplTest {

    RoomOrderServiceImpl roomOrderService;

    @BeforeMethod
    public void initialize() {
        roomOrderService = new RoomOrderServiceImpl();
    }

    @Test
    public void testCountDays() throws ServiceException {
        int actual = roomOrderService.countDays("2022-01-01", "2022-01-05");
        int expected = 4;
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "wrongDataRangeProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"2022-01-05", "2022-01-01"},
                {"2022-01-01", "2023-01-01"},
        };
    }

    @Test(dataProvider = "wrongDataRangeProvider",
            expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = ".*wrong date range.*")
    public void testCountDaysExDateRange(String from, String to) throws ServiceException {
        roomOrderService.countDays(from, to);
    }

    @Test
    public void testCountBaseAmount() throws ServiceException {
        BigDecimal actual = roomOrderService.countBaseAmount(5, new BigDecimal(100));
        BigDecimal expected = new BigDecimal(500);
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCountBaseAmountException() throws ServiceException {
        roomOrderService.countBaseAmount(5, new BigDecimal(-100));
    }

    @Test()
    public void testCountTotalAmount() throws ServiceException {
        BigDecimal actual = roomOrderService.countTotalAmount(5, new BigDecimal(100), 10);
        BigDecimal expected = new BigDecimal("450.0");
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCountTotalAmountExcpected() throws ServiceException {
        roomOrderService.countTotalAmount(5, new BigDecimal(-100), 10);
    }

    @Test
    public void testCreateСanBeCanceledMap() {
        RoomOrder order1 = RoomOrder.newBuilder()
                .setEntityId(15L)
                .setStatus(RoomOrder.Status.NEW)
                .setFrom(LocalDate.parse("2023-03-03"))
                .build();
        RoomOrder order2 = RoomOrder.newBuilder()
                .setEntityId(18L)
                .setStatus(RoomOrder.Status.CONFIRMED)
                .setFrom(LocalDate.parse("2023-03-03"))
                .build();
        RoomOrder order3 = RoomOrder.newBuilder()
                .setEntityId(25L)
                .setStatus(RoomOrder.Status.NEW)
                .setFrom(LocalDate.parse("2022-01-01"))
                .build();
        List<RoomOrder> orders = List.of(order1, order2, order3);
        Map<Long,Boolean> actual = roomOrderService.createСanBeCanceledMap(orders);
        Map<Long,Boolean> expected = new HashMap<>();
        expected.put(15L, true);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCreateСanBeUpdatedMap() {
        RoomOrder order1 = RoomOrder.newBuilder()
                .setEntityId(15L)
                .setStatus(RoomOrder.Status.NEW)
                .build();
        RoomOrder order2 = RoomOrder.newBuilder()
                .setEntityId(18L)
                .setStatus(RoomOrder.Status.CONFIRMED)
                .build();
        RoomOrder order3 = RoomOrder.newBuilder()
                .setEntityId(25L)
                .setStatus(RoomOrder.Status.CANCELED_BY_ADMIN)
                .build();
        RoomOrder order4 = RoomOrder.newBuilder()
                .setEntityId(28L)
                .setStatus(RoomOrder.Status.CANCELED_BY_CLIENT)
                .build();
        RoomOrder order5 = RoomOrder.newBuilder()
                .setEntityId(31L)
                .setStatus(RoomOrder.Status.IN_PROGRESS)
                .build();
        List<RoomOrder> orders = List.of(order1, order2, order3, order4, order5);
        Map<Long,Boolean> actual = roomOrderService.createСanBeUpdatedMap(orders);
        Map<Long,Boolean> expected = new HashMap<>();
        expected.put(15L, true);
        expected.put(18L, true);
        expected.put(31L, true);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "statusProvider")
    public Object[][] createData2() {
        return new Object[][]{
                {RoomOrder.Status.NEW, Arrays.asList(RoomOrder.Status.CONFIRMED, RoomOrder.Status.CANCELED_BY_ADMIN)},
                {RoomOrder.Status.CONFIRMED, Arrays.asList(RoomOrder.Status.IN_PROGRESS, RoomOrder.Status.CANCELED_BY_ADMIN)},
                {RoomOrder.Status.IN_PROGRESS, Arrays.asList(RoomOrder.Status.COMPLETED)},
                {RoomOrder.Status.COMPLETED, new ArrayList<RoomOrder.Status>()},
                {RoomOrder.Status.CANCELED_BY_CLIENT, new ArrayList<RoomOrder.Status>()},
                {RoomOrder.Status.CANCELED_BY_ADMIN, new ArrayList<RoomOrder.Status>()},
        };
    }

    @Test(dataProvider = "statusProvider")
    public void testFindAvailableStatuses(RoomOrder.Status status, List<RoomOrder.Status> expected) {
        List<RoomOrder.Status> actual = roomOrderService.findAvailableStatuses(status);
        Assert.assertEquals(actual, expected);
    }

    @AfterMethod
    public void clean() {
        roomOrderService = null;
    }
}