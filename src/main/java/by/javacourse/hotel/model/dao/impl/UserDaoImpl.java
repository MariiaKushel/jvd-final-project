package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.exception.DaoException;

import static by.javacourse.hotel.model.dao.ColumnName.*;

import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.UserMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class UserDaoImpl implements UserDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_USER = """
            INSERT INTO hotel.users (email, role, name, phone_number, status, discount_id, balance, password) 
            VALUES (?,?,?,?,?,?,?,?)""";
    private static final String SQL_UPDATE_PASSWORD =
            "UPDATE hotel.users SET password=? WHERE user_id=?";
    private static final String SQL_UPDATE_BALANCE =
            "UPDATE hotel.users SET balance=balance+? WHERE user_id=?";

    private static final String SQL_UPDATE_USER = """
            UPDATE hotel.users SET name=?, role=?, phone_number=?, status=?, discount_id=? 
            WHERE user_id=?""";


    private static final String SQL_SELECT_USER = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users""";

    private static final String BY_EMAIL = " WHERE email=? LIMIT 1";
    private static final String BY_EMAIL_AND_PASSWORD = " WHERE email=? AND password=? LIMIT 1";
    private static final String BY_ID = " WHERE user_id=? LIMIT 1";

    private static final String WHERE_PARAM = " WHERE ";
    private static final String EMAIL_PART = " email LIKE ? ";
    private static final String PHONE_PART = " phone_number LIKE ? ";
    private static final String NAME_PART = " name LIKE ? ";
    private static final String STATUS = " status=? ";
    private static final String AND = " AND ";

    private static final String SQL_SELECT_DISCOUNT_BY_USER_ID = """
            SELECT rate
            FROM hotel.users
            JOIN hotel.discounts ON hotel.users.discount_id=hotel.discounts.discount_id
            AND user_id=? LIMIT 1""";

    private static final String SQL_SELECT_BALANCE_BY_USER_ID = """
            SELECT balance
            FROM hotel.users
            WHERE user_id=? LIMIT 1""";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_USER)) {
            users = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.users was failed " + e);
            throw new DaoException("SQL request findAll from table hotel.users was failed", e);
        }
        return users;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        logger.error("Unavailable operation to entity <User>");
        throw new UnsupportedOperationException("Unavailable operation to entity <User>");
    }

    @Override
    public boolean create(User user) throws DaoException {
        logger.error("Unavailable operation to entity <User>");
        throw new UnsupportedOperationException("Unavailable operation to entity <User>");
       /* int rowsInserted = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getStatus().toString());
            statement.setLong(6, user.getDiscountId());
            statement.setBigDecimal(7, user.getBalance());
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.users was failed" + e);
            throw new DaoException("SQL request create from table hotel.users was failed", e);
        }
        return rowsInserted == 1;*/
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> oldUser = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_ID,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, user.getEntityId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //get old user
                    oldUser = mapper.retrieve(resultSet).stream().findFirst();
                    //update user
                    resultSet.first();
                    resultSet.updateString(EMAIL_PART, user.getEmail());
                    resultSet.updateString(ROLE, user.getRole().toString());
                    resultSet.updateString(NAME_PART, user.getName());
                    resultSet.updateString(PHONE_NUMBER, user.getPhoneNumber());
                    resultSet.updateString(USER_STATUS, user.getStatus().toString());
                    resultSet.updateLong(DISCOUNT_ID, user.getDiscountId());
                    resultSet.updateBigDecimal(BALANCE, user.getBalance());
                    resultSet.updateRow();
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request update from table hotel.users was failed " + e);
            throw new DaoException("SQL request update from table hotel.users was failed", e);
        }
        return oldUser;
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request isEmailExist from table hotel.users was failed");
            throw new DaoException("SQL request isEmailExist from table hotel.users was failed", e);
        }
        return isExist;
    }

    @Override
    public boolean isUserExist(String email, String password) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request isUserExist from table hotel.users was failed " + e);
            throw new DaoException("SQL request isUserExist from table hotel.users was failed", e);
        }
        return isExist;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException {
        Optional<User> user = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                user = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findUserByEmailAndPassword from table hotel.users was failed " + e);
            throw new DaoException("SQL request findUserByEmailAndPassword from table hotel.users was failed", e);
        }
        return user;
    }

    @Override
    public boolean changePassword(long userId, String newPassword) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request changePassword from table hotel.users was failed " + e);
            throw new DaoException("SQL request changePassword from table hotel.users was failed", e);
        }
        return rowsUpdated == 1;
    }

    @Override
    public boolean createUserWithPassword(User user, String password) throws DaoException {
        int rowsInserted = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getStatus().toString());
            statement.setLong(6, user.getDiscountId());
            statement.setBigDecimal(7, user.getBalance());
            statement.setString(8, password);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request createUserWithPassword from table hotel.users was failed" + e);
            throw new DaoException("SQL request createUserWithPassword from table hotel.users was failed", e);
        }
        return rowsInserted == 1;
    }

    @Override
    public int findDiscountByUserId(long userId) throws DaoException {
        int rate = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    rate = resultSet.getInt(RATE);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request findDiscountByUserId from table hotel.users was failed" + e);
            throw new DaoException("SQL request findDiscountByUserId from table hotel.users was failed", e);
        }
        return rate;
    }

    @Override
    public BigDecimal findBalanceByUserId(long userId) throws DaoException {
        BigDecimal balance = new BigDecimal(0);
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BALANCE_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getBigDecimal(BALANCE);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request findBalanceByUserId from table hotel.users was failed" + e);
            throw new DaoException("SQL request findBalanceByUserId from table hotel.users was failed", e);
        }
        return balance;
    }

    @Override
    public Optional<User> findUserById(long userId) throws DaoException {
        Optional<User> user = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                user = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findUserById from table hotel.users was failed " + e);
            throw new DaoException("SQL request findUserById from table hotel.users was failed", e);
        }
        return user;
    }

    @Override
    public boolean update1(User user) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getStatus().toString());
            statement.setLong(5, user.getDiscountId());
            statement.setLong(6, user.getEntityId());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.users was failed" + e);
            throw new DaoException("SQL request update1 from table hotel.users was failed", e);
        }
        return rowsUpdated == 1;
    }

    @Override
    public List<User> findUserByParameter(String status, String emailPart, String phonePart, String namePart) throws DaoException {
        List<String> activeParameter = new ArrayList<>();
        StringBuilder sqlRequest = new StringBuilder();
        if (!status.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER).append(WHERE_PARAM).append(STATUS)
                    : sqlRequest.append(AND).append(STATUS);
            activeParameter.add(status);
        }
        if (!emailPart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER).append(WHERE_PARAM).append(EMAIL_PART)
                    : sqlRequest.append(AND).append(EMAIL_PART);
            activeParameter.add("%" + emailPart + "%");
        }
        if (!phonePart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER).append(WHERE_PARAM).append(PHONE_PART)
                    : sqlRequest.append(AND).append(PHONE_PART);
            activeParameter.add("%" + phonePart + "%");
        }
        if (!namePart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER).append(WHERE_PARAM).append(NAME_PART)
                    : sqlRequest.append(AND).append(NAME_PART);
            activeParameter.add("%" + namePart + "%");
        }
        List<User> users = new ArrayList<>();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest.toString())) {
            for (int i = 0; i < activeParameter.size(); i++) {
                statement.setString(i + 1, activeParameter.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                users = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findUserByParameter from table hotel.users was failed " + e);
            throw new DaoException("SQL request findUserByParameter from table hotel.users was failed", e);
        }
        return users;
    }

    @Override
    public boolean updateBalance(long userId, BigDecimal amount) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BALANCE)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, userId);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request updateBalance from table hotel.users was failed" + e);
            throw new DaoException("SQL request updateBalance from table hotel.users was failed", e);
        }
        return rowsUpdated == 1;
    }

}
