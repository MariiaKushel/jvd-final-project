package test.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DiscountDao;
import by.javacourse.hotel.model.service.impl.DiscountServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.*;

import static by.javacourse.hotel.controller.command.RequestAttribute.RATE_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.DISCOUNT_ID_SES;
import static by.javacourse.hotel.controller.command.SessionAttribute.RATE_SES;

public class DiscountServiceImplTest {

    private DiscountServiceImpl discountService;
    private DiscountDao discountDaoMock;

    @BeforeMethod
    public void initialize() throws DaoException {
        discountService = new DiscountServiceImpl();
        discountDaoMock = Mockito.mock(DiscountDao.class);
        setMock(discountDaoMock);
    }

    @Test
    public void testFindAllDiscount() throws DaoException, ServiceException {
        List<Discount> discounts = new ArrayList<>();
        Mockito.when(discountDaoMock.findAll())
                .thenReturn(discounts);
        List<Discount> actual = discountService.findAllDiscount();
        List<Discount> expected = new ArrayList<>();
        Mockito.verify(discountDaoMock, Mockito.times(1)).findAll();
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllDiscountException() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.findAll())
                .thenThrow(new DaoException());
        discountService.findAllDiscount();
        Mockito.verify(discountDaoMock, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindDiscountByRate() throws DaoException, ServiceException {
        Discount discount1 = Discount.newBuilder().setEntityId(42).setRate(5).build();
        Discount discount2 = Discount.newBuilder().setEntityId(24).setRate(5).build();
        List<Discount> discounts = List.of(discount1, discount2);
        Mockito.when(discountDaoMock.findDiscountByRate(5))
                .thenReturn(discounts);
        Map<String, String> discountMap = new HashMap<>();
        discountMap.put(RATE_ATR, "5");
        List<Discount> actual = discountService.findDiscountByRate(discountMap);
        List<Discount> expected = discounts;
        Mockito.verify(discountDaoMock, Mockito.times(1)).findDiscountByRate(5);
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindDiscountByRateExeption() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.findDiscountByRate(5))
                .thenThrow(new DaoException());
        Map<String, String> discountMap = new HashMap<>();
        discountMap.put(RATE_ATR, "5");
        discountService.findDiscountByRate(discountMap);
        Mockito.verify(discountDaoMock, Mockito.times(1)).findDiscountByRate(5);
    }

    @Test
    public void testFindDiscountById() throws DaoException, ServiceException {
        Optional<Discount> discount = Optional.of(Discount.newBuilder().setEntityId(42).setRate(5).build());
        Mockito.when(discountDaoMock.findEntityById(Mockito.anyLong()))
                .thenReturn(discount);
        Optional<Discount> actual = discountService.findDiscountById("42");
        Optional<Discount> expected = discount;
        Mockito.verify(discountDaoMock, Mockito.times(1)).findEntityById(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindDiscountByIdExeption() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.findEntityById(Mockito.anyLong()))
                .thenThrow(new DaoException());
        discountService.findDiscountById("42");
        Mockito.verify(discountDaoMock, Mockito.times(1)).findEntityById(Mockito.anyLong());
    }

    @Test
    public void testFindDiscountByUserId() throws DaoException, ServiceException {
        Optional<Discount> discount = Optional.of(Discount.newBuilder().setEntityId(42).setRate(5).build());
        Mockito.when(discountDaoMock.findDiscountByUserId(Mockito.anyLong()))
                .thenReturn(discount);
        Optional<Discount> actual = discountService.findDiscountByUserId("55");
        Optional<Discount> expected = discount;
        Mockito.verify(discountDaoMock, Mockito.times(1)).findDiscountByUserId(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindDiscountByUserIdException() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.findDiscountByUserId(Mockito.anyLong()))
                .thenThrow(new DaoException());
        discountService.findDiscountByUserId("55");
        Mockito.verify(discountDaoMock, Mockito.times(1)).findDiscountByUserId(Mockito.anyLong());
    }

    @Test
    public void testUpdateDiscount() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.update(Mockito.any()))
                .thenReturn(true);
        Map<String, String> discountData = new HashMap<>();
        discountData.put(DISCOUNT_ID_SES, "5");
        discountData.put(RATE_SES, "10");
        Assert.assertTrue(discountService.updateDiscount(discountData));
        Mockito.verify(discountDaoMock, Mockito.times(1)).update(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateDiscountException() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.update(Mockito.any()))
                .thenThrow(new DaoException());
        Map<String, String> discountData = new HashMap<>();
        discountData.put(DISCOUNT_ID_SES, "5");
        discountData.put(RATE_SES, "10");
        discountService.updateDiscount(discountData);
        Mockito.verify(discountDaoMock, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void testCreateDiscount() throws ServiceException, DaoException {
        Mockito.when(discountDaoMock.create(Mockito.any()))
                .thenReturn(true);
        Map<String, String> discountData = new HashMap<>();
        discountData.put(RATE_SES, "10");
        Assert.assertTrue(discountService.createDiscount(discountData));
        Mockito.verify(discountDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateDiscountException() throws ServiceException, DaoException {
        Mockito.when(discountDaoMock.create(Mockito.any()))
                .thenThrow(new DaoException());
        Map<String, String> discountData = new HashMap<>();
        discountData.put(RATE_SES, "10");
        discountService.createDiscount(discountData);
        Mockito.verify(discountDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void testRemoveDiscount() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.delete(Mockito.any()))
                .thenReturn(true);
        Discount discount = Discount.newBuilder().setEntityId(5).setRate(10).build();
        Assert.assertTrue(discountService.removeDiscount(discount));
        Mockito.verify(discountDaoMock, Mockito.times(1)).delete(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRemoveDiscountException() throws DaoException, ServiceException {
        Mockito.when(discountDaoMock.delete(Mockito.any()))
                .thenThrow(new DaoException());
        Discount discount = Discount.newBuilder().setEntityId(5).setRate(10).build();
        discountService.removeDiscount(discount);
        Mockito.verify(discountDaoMock, Mockito.times(1)).delete(Mockito.any());
    }

    @AfterMethod
    public void clean() {
        discountService = null;
    }

    private void setMock(DiscountDao mock) {
        try {
            Field discountDao = DiscountServiceImpl.class.getDeclaredField("discountDao");
            discountDao.setAccessible(true);
            discountDao.set(discountService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}