package by.javacourse.hotel.model.service.impl;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.PasswordEncryptor;
import by.javacourse.hotel.validator.UserValidator;
import by.javacourse.hotel.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();

    private DaoProvider provider = DaoProvider.getInstance();
    private UserDao userDao = provider.getUserDao();

    @Override
    public boolean createNewAccount(Map<String, String> userData, String password) throws ServiceException {
        UserValidator validator = UserValidatorImpl.getInstance();
        String email = userData.get(EMAIL);
        String name = userData.get(NAME);
        String phoneNumber = userData.get(PHONE_NUMBER);
        String repeatPassword = userData.get(REPEAT_PASSWORD);
        System.out.println("password " + password);
        System.out.println("repeatPassword " + repeatPassword);

        boolean isCreated = true;
        if (!password.equals(repeatPassword)) {
            logger.info("Passwords mismatch");
            isCreated = false;
        }
        if(!validator.validateEmail(email)){
            logger.info("Email is not valid");
            isCreated = false;
        }
        if(!validator.validatePassword(password)){
            logger.info("Password is not valid");
            isCreated = false;
        }
        if(!validator.validateName(name)){
            logger.info("Name is not valid");
            isCreated = false;
        }
        if(!validator.validatePhoneNumber(phoneNumber)){
            logger.info("Phone number is not valid");
            isCreated = false;
        }
        if (!isCreated) {
            return isCreated;
        }

        try {
            if (userDao.isEmailExist(email)) {
                logger.info("Email is already in use");
                isCreated = false;
                return isCreated;
            }
            User newUser = User.newBuilder()
                    .setEmail(email)
                    .setName(name)
                    .setPhoneNumber(phoneNumber)
                    .build();
            String secretPassword = PasswordEncryptor.encrypt(password);
            isCreated = userDao.createUserWithPassword(newUser, secretPassword);
        } catch (DaoException e) {
            logger.error("Try to register new user was failed " + e);
            throw new ServiceException("Try to register new user was failed", e);
        }
        return isCreated;
    }

    @Override
    public Optional<User> authenticate(String email, String password) throws ServiceException {
        Optional<User> user = Optional.empty();
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateEmail(email) || !validator.validatePassword(password)) {
            logger.info("Email or password has not valid value");
            return user;
        }
        try {
            String secretPassword = PasswordEncryptor.encrypt(password);
            user = userDao.findUserByEmailAndPassword(email, secretPassword);
        } catch (DaoException e) {
            logger.error("Try to authenticate user was failed " + e);
            throw new ServiceException("Try to authenticate user was failed", e);
        }
        return user;
    }

    @Override //Fixme rewrite
    public Optional<User> updatePersonalData(User user, String password) throws ServiceException {
        Optional<User> oldUser = Optional.empty();
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validate(user, password)) {
            logger.info("Some personal data has not valid value");
            return oldUser;
        }
        try {
            oldUser = userDao.update(user);
        } catch (DaoException e) {
            logger.error("Try to update personal data was failed " + e);
            throw new ServiceException("Try to update personal data was failed", e);
        }
        return oldUser;
    }
}
