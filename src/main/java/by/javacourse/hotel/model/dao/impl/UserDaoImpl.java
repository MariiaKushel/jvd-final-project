package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.UserMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.javacourse.hotel.model.dao.ColumnName.BALANCE;

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
    private static final String SQL_SELECT_ALL_USER = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users""";
    private static final String SQL_SELECT_USER_BY_ID = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users
            WHERE user_id=? LIMIT 1""";
    private static final String SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users
            WHERE email=? AND password=? LIMIT 1""";
    private static final String SQL_SELECT_USER_BY_EMAIL = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users
            WHERE email=? LIMIT 1""";
    private static final String SQL_SELECT_BALANCE_BY_USER_ID = """
            SELECT balance
            FROM hotel.users
            WHERE user_id=? LIMIT 1""";
    private static final String SQL_SELECT_USER_BY_PARAMETERS = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users
            WHERE """;
    private static final String EMAIL_PART = " email LIKE ? ";
    private static final String PHONE_PART = " phone_number LIKE ? ";
    private static final String NAME_PART = " name LIKE ? ";
    private static final String STATUS = " status=? ";
    private static final String AND = " AND ";
    private static final String PERSENT_SIGN = "%";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER)) {
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
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
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
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException {
        Optional<User> user = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
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
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request changePassword from table hotel.users was failed " + e);
            throw new DaoException("SQL request changePassword from table hotel.users was failed", e);
        }
        return rows == 1;
    }

    @Override
    public boolean createUserWithPassword(User user, String password) throws DaoException {
        int rows = 0;
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
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request createUserWithPassword from table hotel.users was failed" + e);
            throw new DaoException("SQL request createUserWithPassword from table hotel.users was failed", e);
        }
        return rows == 1;
    }

    @Override
    public BigDecimal findBalanceByUserId(long userId) throws DaoException {
        BigDecimal balance = BigDecimal.ZERO;
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

    public Optional<User> findEntityById(Long userId) throws DaoException {
        Optional<User> user = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
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
    public boolean update(User user) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getRole().toString());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getStatus().toString());
            statement.setLong(5, user.getDiscountId());
            statement.setLong(6, user.getEntityId());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.users was failed" + e);
            throw new DaoException("SQL request update1 from table hotel.users was failed", e);
        }
        return rows == 1;
    }

    @Override
    public List<User> findUserByParameter(String status, String emailPart, String phonePart, String namePart) throws DaoException {
        List<String> activeParameter = new ArrayList<>();
        StringBuilder sqlRequest = new StringBuilder();
        if (!status.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER_BY_PARAMETERS).append(STATUS)
                    : sqlRequest.append(AND).append(STATUS);
            activeParameter.add(status);
        }
        if (!emailPart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER_BY_PARAMETERS).append(EMAIL_PART)
                    : sqlRequest.append(AND).append(EMAIL_PART);
            activeParameter.add(PERSENT_SIGN + emailPart + PERSENT_SIGN);
        }
        if (!phonePart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER_BY_PARAMETERS).append(PHONE_PART)
                    : sqlRequest.append(AND).append(PHONE_PART);
            activeParameter.add(PERSENT_SIGN + phonePart + PERSENT_SIGN);
        }
        if (!namePart.isEmpty()) {
            sqlRequest = sqlRequest.isEmpty()
                    ? sqlRequest.append(SQL_SELECT_USER_BY_PARAMETERS).append(NAME_PART)
                    : sqlRequest.append(AND).append(NAME_PART);
            activeParameter.add(PERSENT_SIGN + namePart + PERSENT_SIGN);
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
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BALANCE)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, userId);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request updateBalance from table hotel.users was failed" + e);
            throw new DaoException("SQL request updateBalance from table hotel.users was failed", e);
        }
        return rows == 1;
    }

}
