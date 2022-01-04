package test.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.impl.UserDaoImpl;
import by.javacourse.hotel.entity.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class UserDaoTest {
    private UserDaoImpl userDao;
    private User user;

    @BeforeMethod
    public void init() {
        userDao = new UserDaoImpl();
        user = User.newBuilder()
                .setEmail("ivan_ivanov@gmail.com")
                //.setPassword("12345678");
                .setRole(User.Role.CLIENT)
                .setName("Иван")
                .setPhoneNumber("+375291111111")
                .setStatus(User.Status.NEW)
                .setDiscountId(1)
                .setBalance(new BigDecimal("100.00"))
                .build();
    }

    @Test
    public void testCreatePositive() throws DaoException {
        boolean condition = false;
        condition = userDao.create(user);
        Assert.assertTrue(condition);
    }

    @Test(expectedExceptions = DaoException.class)
    public void testCreateException() throws DaoException {
        /*user.setPhoneNumber("+3752911111118888");
        userDao.create(user);*/
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testDeleteException() throws DaoException {
        userDao.delete(user);
    }

    @Test //TODO change realization, it is not universal
    public void testUpdatePositive() throws DaoException {
       /* user.setEntityId(5);
        User expected = new User();
        expected.setEntityId(user.getEntityId());
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
        Assert.assertEquals(actual, expected);*/
    }

    @Test
    public void testUpdateNegative() throws DaoException {
       /* user.setEntityId(-5);
        Optional<User> optionalUser = userDao.update(user);
        Assert.assertTrue(optionalUser.isEmpty());*/
    }

    @Test(expectedExceptions = DaoException.class)
    public void testUpdateException() throws DaoException {
      /*  user.setEntityId(6);
        user.setPhoneNumber("11111111111111111");
        userDao.update(user);*/
    }

    @Test
    public void testFindAll() throws DaoException {
        List<User> users = userDao.findAll();
        Assert.assertTrue(users != null);
    }

    @AfterMethod
    public void clean() {
        userDao = null;
        user = null;
    }
}
