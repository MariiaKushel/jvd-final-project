package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.exception.DaoException;

import static by.javacourse.hotel.model.dao.ColumnName.*;

import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.entity.User;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_USER =
            "INSERT INTO hotel.users (login, password, role, name, phone_number, status, discount_id, balance)"
                    + " VALUES (?,?,?,?,?,?,?,?)";

    private static final String SQL_SELECT_USER =
            "SELECT user_id, login, password, role, name, phone_number, status, discount_id, balance"
                    + " FROM hotel.users";
    private static final String BY_LOGIN = " WHERE login=? LIMIT 1";
    private static final String BY_LOGIN_AND_PASSWORD = " WHERE login=? AND password=? LIMIT 1";
    private static final String BY_ID = " WHERE user_id=? LIMIT 1";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_USER)) {
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                users.add(user);
            }
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
        int rowsInsert = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getName());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getStatus().toString());
            statement.setLong(7, user.getDiscountId());
            statement.setBigDecimal(8, user.getBalance());
            rowsInsert = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.users was failed" + e);
            throw new DaoException("SQL request create from table hotel.users was failed", e);
        }
        return rowsInsert == 1;
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> oldUser = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_ID, //TODO can use "" + "" ?
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //get old user
                    oldUser = Optional.of(createUserFromResultSet(resultSet));
                    //update user
                    resultSet.updateString(LOGIN, user.getLogin());
                    resultSet.updateString(PASSWORD, user.getPassword());
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
    public boolean isLoginExsist(String login) throws DaoException {
        boolean isUse = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_LOGIN)) { //TODO can use "" + "" ?
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUse = true;
            }
        } catch (SQLException e) {
            logger.error("SQL request isLoginExsist from table hotel.users was failed");
            throw new DaoException("SQL request isLoginExsist from table hotel.users was failed", e);
        }
        return isUse;
    }

    @Override
    public boolean isUserExsist(String login, String password) throws DaoException {
        boolean isExsist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER + BY_LOGIN_AND_PASSWORD)) { //TODO can use "" + "" ?
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isExsist = true;
            }
        } catch (SQLException e) {
            logger.error("SQL request isUserExsist from table hotel.users was failed " + e);
            throw new DaoException("SQL request isUserExsist from table hotel.users was failed", e);
        }
        return isExsist;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException { //TODO make separate class or inner/nested class
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setRole(User.Role.valueOf(resultSet.getString(ROLE).toUpperCase()));
        user.setName(resultSet.getString(NAME));
        user.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
        user.setStatus(User.Status.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
        user.setDiscountId(resultSet.getLong(DISCOUNT_ID));
        user.setBalance(resultSet.getBigDecimal(BALANCE));
        return user;
    }
}
