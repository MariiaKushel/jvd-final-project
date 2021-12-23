package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.entity.User;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.PasswordEncryptor;
import by.javacourse.hotel.util.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();

    private DaoProvider provider = DaoProvider.getInstance();
    private UserDao userDao = provider.getUserDao();

    @Override
    public boolean register(User user) throws ServiceException { // TODO many returns ok?
        if (!UserValidator.validate(user)) {
            logger.info("Login or password has not valid value");
            return false;
        }
        try {
            if (userDao.isLoginExsist(user.getLogin())) {
                logger.info("Login is already in use");
                return false;
            }
            String secretPassword = PasswordEncryptor.encrypt(user.getPassword());
            user.setPassword(secretPassword);
            userDao.create(user);
        } catch (DaoException e) {
            logger.error("Try to register new user was failed " + e);
            throw new ServiceException("Try to register new user was failed", e);
        }
        return true;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException { // TODO many returns ok?
        if (!UserValidator.validateLogin(login) || !UserValidator.validatePassword(password)) {
            logger.info("Login or password has not valid value");
            return false;
        }
        boolean isAuthenticate = false;
        try {
            String secretPassword = PasswordEncryptor.encrypt(password);
            isAuthenticate = userDao.isUserExsist(login, secretPassword);
        } catch (DaoException e) {
            logger.error("Try to authenticate user was failed " + e);
            throw new ServiceException("Try to authenticate user was failed", e);
        }
        return isAuthenticate;
    }

    @Override
    public Optional<User> updatePersonalData(User user) throws ServiceException {
        if (!UserValidator.validate(user)) {
            logger.info("Some personal data has not valid value");
            return Optional.empty();
        }
        Optional<User> oldUser = Optional.empty();
        try {
            oldUser = userDao.update(user);
        } catch (DaoException e) {
            logger.error("Try to update personal data was failed " + e);
            throw new ServiceException("Try to update personal data was failed", e);
        }
        return oldUser;
    }
}
