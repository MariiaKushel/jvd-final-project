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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_USER = """
            INSERT INTO hotel.users (email, role, name, phone_number, status, discount_id, balance) 
            VALUES (?,?,?,?,?,?,?)""";
    private static final String SQL_UPDATE_PASSWORD =
            "UPDATE hotel.users SET password=? WHERE email=?";
    private static final String SQL_SELECT_USER = """
            SELECT user_id, email, role, name, phone_number, status, discount_id, balance 
            FROM hotel.users""";
    private static final String BY_EMAIL = " WHERE email=? LIMIT 1";
    private static final String BY_EMAIL_AND_PASSWORD = " WHERE email=? AND password=? LIMIT 1";
    private static final String BY_ID = " WHERE user_id=? LIMIT 1";

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
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.users was failed" + e);
            throw new DaoException("SQL request create from table hotel.users was failed", e);
        }
        return rowsInserted == 1;
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> oldUser = Optional.empty();
        Mapper mapper = UserMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_ID,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) { // FIXME need TYPE_SCROLL_INSENSITIVE?
            statement.setLong(1, user.getEntityId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //get old user
                    oldUser = mapper.retrieve(resultSet).stream().findFirst();
                    //update user
                    resultSet.updateString(EMAIL, user.getEmail());
                    resultSet.updateString(ROLE, user.getRole().toString());
                    resultSet.updateString(NAME, user.getName());
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
        System.out.println(oldUser);
        return oldUser;
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_EMAIL)) { //TODO can use "" + "" ?
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
    public boolean changePassword(String email, String newPassword) throws DaoException {//FIXME rename
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setString(2, email);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request changePassword from table hotel.users was failed " + e);
            throw new DaoException("SQL request changePassword from table hotel.users was failed", e);
        }
        return rowsUpdated == 1;
    }

}
