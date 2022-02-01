package by.javacourse.hotel.model.dao.impl;

import static by.javacourse.hotel.model.dao.ColumnName.*;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.DiscountDao;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.model.dao.mapper.impl.DiscountMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@code DiscountDaoImpl} class implements functional of {@link DiscountDao}
 */
public class DiscountDaoImpl implements DiscountDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_DISCOUNT = """
            INSERT INTO hotel.discounts (rate) VALUES (?)""";
    private static final String SQL_UPDATE_DISCOUNT = """
            UPDATE hotel.discounts SET rate=? WHERE discount_id=?""";
    private static final String SQL_DELETE_DISCOUNT = """
            DELETE FROM hotel.discounts WHERE discount_id=?""";
    private static final String SQL_SELECT_ALL_DISCOUNT = """
            SELECT discount_id, rate FROM hotel.discounts
            ORDER BY rate""";
    private static final String SQL_SELECT_DISCOUNT_BY_ID = """
            SELECT discount_id, rate FROM hotel.discounts
            WHERE discount_id=? LIMIT 1""";
    private static final String SQL_SELECT_DISCOUNT_BY_RATE = """
            SELECT discount_id, rate FROM hotel.discounts
            WHERE rate=? LIMIT 1""";
    private static final String SQL_SELECT_DISCOUNT_BY_USER_ID = """
            SELECT hotel.discounts.discount_id, rate
            FROM hotel.users
            JOIN hotel.discounts ON hotel.users.discount_id=hotel.discounts.discount_id
            AND user_id=? LIMIT 1""";

    @Override
    public List<Discount> findAll() throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_DISCOUNT)) {
            discounts = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.discounts was failed");
            throw new DaoException("SQL request findAll from table discounts.users was failed", e);
        }
        return discounts;
    }

    @Override
    public boolean delete(Discount discount) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DISCOUNT)) {
            statement.setLong(1, discount.getEntityId());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request delete from table hotel.discounts was failed");
            throw new DaoException("SQL request delete from table hotel.discounts was failed", e);
        }
        return rows == 1;
    }

    @Override
    public boolean create(Discount discount) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DISCOUNT)) {
            statement.setInt(1, discount.getRate());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.discounts was failed");
            throw new DaoException("SQL request create from table hotel.discounts was failed", e);
        }
        return rows == 1;
    }

    @Override
    public boolean update(Discount discount) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DISCOUNT)) {
            statement.setLong(1, discount.getRate());
            statement.setLong(2, discount.getEntityId());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.discounts was failed");
            throw new DaoException("SQL request update1 from table hotel.discounts was failed", e);
        }
        return rowsUpdated == 1;
    }

    @Override
    public List<Discount> findDiscountByRate(int rate) throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT_BY_RATE)) {
            statement.setInt(1, rate);
            try (ResultSet resultSet = statement.executeQuery()) {
                discounts = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findDiscountByRate from table hotel.discounts was failed");
            throw new DaoException("SQL request findDiscountByRate from table hotel.discounts was failed", e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findDiscountByUserId(long userId) throws DaoException {
        Optional<Discount> discount = Optional.empty();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                    discount = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findDiscountByUserId from table hotel.users was failed" + e);
            throw new DaoException("SQL request findDiscountByUserId from table hotel.users was failed", e);
        }
        return discount;
    }

    public Optional<Discount> findEntityById(Long discountId) throws DaoException {
        Optional<Discount> discount = Optional.empty();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT_BY_ID)) {
            statement.setLong(1, discountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                discount = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findEntityById from table hotel.discounts was failed");
            throw new DaoException("SQL request findEntityById from table hotel.discounts was failed", e);
        }
        return discount;
    }
}
