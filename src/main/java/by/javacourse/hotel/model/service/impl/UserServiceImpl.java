package by.javacourse.hotel.model.service.impl;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

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

import java.math.BigDecimal;
import java.util.*;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();

    private DaoProvider provider = DaoProvider.getInstance();
    private UserDao userDao = provider.getUserDao();

    @Override
    public boolean createNewAccount(Map<String, String> userData) throws ServiceException {
        boolean isCreated = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateUserDataCreate(userData)) {
            return isCreated;
        }

        String email = userData.get(EMAIL_SES);
        String name = userData.get(NAME_SES);
        String phoneNumber = userData.get(PHONE_NUMBER_SES);
        String password = userData.get(PASSWORD_SES);
        try {
            if (userDao.isEmailExist(email)) {
                userData.put(WRONG_EMAIL_EXIST_SES, UserValidator.WRONG_DATA_MARKER);
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
            logger.error("Try to createNewAccount new user was failed " + e);
            throw new ServiceException("Try to createNewAccount new user was failed", e);
        }
        return isCreated;
    }

    @Override
    public Optional<User> authenticate(Map<String, String> userData) throws ServiceException {
        Optional<User> user = Optional.empty();

        String email = userData.get(EMAIL_SES);
        String password = userData.get(PASSWORD_SES);
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateEmail(email) || !validator.validatePassword(password)) {
            userData.put(WRONG_EMAIL_OR_PASSWORD_SES, UserValidator.WRONG_DATA_MARKER);
            return user;
        }
        try {
            String secretPassword = PasswordEncryptor.encrypt(password);
            user = userDao.findUserByEmailAndPassword(email, secretPassword);
            if (user.isEmpty()) {
                userData.put(NOT_FOUND_SES, UserValidator.WRONG_DATA_MARKER);
            }
        } catch (DaoException e) {
            logger.error("Try to authenticate user was failed " + e);
            throw new ServiceException("Try to authenticate user was failed", e);
        }
        return user;
    }

    @Override
    public boolean updatePersonalData(Map<String, String> userData) throws ServiceException {
        boolean isUpdated = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateUserDataUpdate(userData)) {
            logger.info("Some personal data has not valid value");
            return isUpdated;
        }

        String email = userData.get(EMAIL_SES);
        String password = userData.get(PASSWORD_SES);
        if (password != null) {
            String secretPassword = PasswordEncryptor.encrypt(password);
            try {
                Optional<User> userCheck = userDao.findUserByEmailAndPassword(email, secretPassword);
                if (userCheck.isEmpty()) {
                    logger.info("Wrong password");
                    userData.put(WRONG_PASSWORD_SES, UserValidator.WRONG_DATA_MARKER);
                    return isUpdated;
                }
            } catch (DaoException e) {
                logger.error("Try to update personal data was failed " + e);
                throw new ServiceException("Try to update personal data was failed", e);
            }
        }

        long userId = Long.parseLong(userData.get(USER_ID_SES));
        String name = userData.get(NAME_SES);
        String phoneNumber = userData.get(PHONE_NUMBER_SES);
        User.Role role = User.Role.valueOf(userData.get(ROLE_SES).toUpperCase());
        User.Status status = User.Status.valueOf(userData.get(USER_STATUS_SES).toUpperCase());
        long discountId = Long.parseLong(userData.get(DISCOUNT_ID_SES));
        BigDecimal balance = new BigDecimal(userData.get(BALANCE_SES));

        User user = User.newBuilder()
                .setEntityId(userId)
                .setEmail(email)
                .setName(name)
                .setPhoneNumber(phoneNumber)
                .setRole(role)
                .setStatus(status)
                .setDiscountId(discountId)
                .setBalance(balance)
                .build();

        try {
            isUpdated = userDao.update(user);
        } catch (DaoException e) {
            logger.error("Try to update personal data was failed " + e);
            throw new ServiceException("Try to update personal data was failed", e);
        }
        return isUpdated;
    }

    @Override
    public Optional<User> findUserById(String userId) throws ServiceException {
        Optional<User> user = Optional.empty();
        try {
            long userIdL = Long.parseLong(userId);
            user = userDao.findEntityById(userIdL);
        } catch (NumberFormatException e) {
            logger.info("Not valid user id");
        } catch (DaoException e) {
            logger.error("Try find user by id was failed " + e);
            throw new ServiceException("Try find user by id was failed", e);
        }
        return user;
    }

    @Override
    public List<User> findUserByParameter(Map<String, String> parameters) throws ServiceException {
        List<User> users = new ArrayList<>();
        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validateUserSearchParameters(parameters)) {
            logger.info("Some search parameters are not valid");
            return users;
        }
        String status = parameters.get(USER_STATUS_ATR);
        String email = parameters.get(EMAIL_ATR);
        String phoneNumber = parameters.get(PHONE_NUMBER_ATR);
        String name = parameters.get(NAME_ATR);

        try {
            if (status.isEmpty() && email.isEmpty() && phoneNumber.isEmpty() && name.isEmpty()) {
                users = userDao.findAll();
            } else {
                users = userDao.findUserByParameter(status, email, phoneNumber, name);
            }
        } catch (DaoException e) {
            logger.error("Try find user by parameter was failed " + e);
            throw new ServiceException("Try find user by parameter was failed", e);
        }
        return users;
    }

    @Override
    public boolean changePassword(Map<String, String> passwordData) throws ServiceException {
        boolean isChange = false;

        long userId = Long.parseLong(passwordData.get(USER_ID_SES));
        String email = passwordData.get(EMAIL_SES);
        String newPassword = passwordData.get(NEW_PASSWORD_SES);
        String oldPassword = passwordData.get(PASSWORD_SES);

        UserValidator validator = UserValidatorImpl.getInstance();
        if (!validator.validatePassword(newPassword)) {
            logger.info("Not valid new password");
            passwordData.put(WRONG_NEW_PASSWORD_SES, UserValidator.WRONG_DATA_MARKER);
            return isChange;
        }
        if (!validator.validatePassword(oldPassword)) {
            logger.info("Not valid old password");
            passwordData.put(WRONG_OLD_PASSWORD_SES, UserValidator.WRONG_DATA_MARKER);
            return isChange;
        }

        String oldSecretPassword = PasswordEncryptor.encrypt(oldPassword);
        try {
            Optional<User> userCheck = userDao.findUserByEmailAndPassword(email, oldSecretPassword);
            if (userCheck.isEmpty()) {
                logger.info("Wrong password");
                passwordData.put(WRONG_PASSWORD_SES, UserValidator.WRONG_DATA_MARKER);
                return isChange;
            }
            String newSecretPassword = PasswordEncryptor.encrypt(newPassword);
            isChange = userDao.changePassword(userId, newSecretPassword);
        } catch (DaoException e) {
            logger.error("Try to update personal data was failed " + e);
            throw new ServiceException("Try to update personal data was failed", e);
        }
        return isChange;
    }

    @Override
    public boolean replenishBalance(Map<String, String> balanceData) throws ServiceException {
        boolean isReplenish = false;
        UserValidator validator = UserValidatorImpl.getInstance();
        String amountAsStr = balanceData.get(REPLENISH_AMOUNT_SES);
        if (!validator.validateAmount(amountAsStr)) {
            logger.info("Not valid value");
            balanceData.put(WRONG_AMOUNT_SES, UserValidator.WRONG_DATA_MARKER);
            return isReplenish;
        }
        long userId = Long.parseLong(balanceData.get(USER_ID_SES));
        BigDecimal amount = new BigDecimal(amountAsStr);
        BigDecimal balance = new BigDecimal(balanceData.get(BALANCE_SES));
        BigDecimal newBalance = balance.add(amount);
        if (newBalance.compareTo(UserValidator.MAX_BALANCE) > 0) {
            logger.info("Amount too much");
            balanceData.put(WRONG_AMOUNT_OVERSIZE_SES, UserValidator.WRONG_DATA_MARKER);
            return isReplenish;
        }
        try {
            isReplenish = userDao.updateBalance(userId, amount);
        } catch (NumberFormatException e) {
            logger.info("Not valid user id");
        } catch (DaoException e) {
            logger.error("Try to replenishBalance was failed " + e);
            throw new ServiceException("Try to replenishBalance data was failed", e);
        }
        return isReplenish;
    }
}
