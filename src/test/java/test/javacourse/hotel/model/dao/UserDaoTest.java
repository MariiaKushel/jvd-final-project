package test.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.impl.UserDaoImpl;
import by.javacourse.hotel.model.entity.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserDaoTest {
    private UserDaoImpl userDao;
    private User user = new User();

    @BeforeMethod
    public void init() {
        userDao = new UserDaoImpl();
        user.setLogin("ivan_ivanov@gmail.com");
        user.setPassword("12345678");
        user.setRole(User.Role.CLIENT);
        user.setName("Иван");
        user.setPhoneNumber("+375291111111");
        user.setStatus(User.Status.NEW);
        user.setDiscountId(1);
        user.setBalance(new BigDecimal("100.00"));
    }

    @Test
    public void testCreatePositive() throws DaoException {
        boolean condition = false;
        condition = userDao.create(user);
        Assert.assertTrue(condition);
    }

    @Test(expectedExceptions = DaoException.class)
    public void testCreateException() throws DaoException {
        user.setPhoneNumber("+3752911111118888");
        userDao.create(user);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testDeleteException() throws DaoException {
        userDao.delete(user);
    }

    @Test //TODO change realization, it is not universal
    public void testUpdatePositive() throws DaoException {
        user.setId(5);
        User expected = new User();
        expected.setId(user.getId());
        expected.setLogin(user.getLogin());
        expected.setPassword(user.getPassword());
        expected.setRole(user.getRole());
        expected.setName(user.getName());
        expected.setStatus(user.getStatus());
        expected.setPhoneNumber(user.getPhoneNumber());
        expected.setDiscountId(user.getDiscountId());
        expected.setBalance(user.getBalance());

        user.setName("Петр");

        Optional<User> optionalUser = userDao.update(user);
        User actual = optionalUser.isPresent() ? optionalUser.get() : new User();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateNegative() throws DaoException {
        user.setId(-5);
        Optional<User> optionalUser = userDao.update(user);
        Assert.assertTrue(optionalUser.isEmpty());
    }

    @Test(expectedExceptions = DaoException.class)
    public void testUpdateException() throws DaoException {
        user.setId(6);
        user.setPhoneNumber("11111111111111111");
        userDao.update(user);
    }

    @Test
    public void testFindAll () throws DaoException {
        List<User> users = userDao.findAll();
        Assert.assertTrue(users!=null);
    }

    @AfterMethod
    public void clean() {
        userDao = null;
        user = null;
    }
}
