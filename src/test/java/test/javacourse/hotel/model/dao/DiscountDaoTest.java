package test.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.impl.DiscountDaoImpl;
import by.javacourse.hotel.entity.Discount;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DiscountDaoTest {

    private DiscountDaoImpl discountDao;
    private Discount discount;

    @BeforeMethod
    public void init() {
        discountDao = new DiscountDaoImpl();
        discount = new Discount();
    }

    @Test
    public void testFindAll() {

    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testCreate() {

    }



    @Test
    public void testFindDiscountByRate() {

    }

    @AfterMethod
    public void clean() {
        discountDao = null;
        discount = null;
    }
}
