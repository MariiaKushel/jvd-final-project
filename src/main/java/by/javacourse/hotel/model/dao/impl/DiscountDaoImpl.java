package by.javacourse.hotel.model.dao.impl;

import static by.javacourse.hotel.model.dao.ColumnName.*;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.DiscountDao;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class
DiscountDaoImpl implements DiscountDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_DISCOUNT =
            "INSERT INTO hotel.discounts (rate) VALUES (?)";

    private static final String SQL_SELECT_DISCOUNT =
            "SELECT discount_id, rate FROM hotel.discounts";

    private static final String BY_RATE =
            " WHERE rate=? LIMIT 1";

    @Override
    public List<Discount> findAll() throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_DISCOUNT)) {
            while (resultSet.next()) {
                Discount discount = createDiscountFromResultSet(resultSet);
                discounts.add(discount);
            }
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.discounts was failed");
            throw new DaoException("SQL request findAll from table discounts.users was failed", e);
        }
        return discounts;
    }

    @Override
    public boolean delete(Discount discount) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Discount discount) throws DaoException {
        int rowsInsert = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DISCOUNT)) {
            statement.setInt(1, discount.getRate());
            rowsInsert = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.discounts was failed");
            throw new DaoException("SQL request create from table hotel.discounts was failed", e);
        }
        return rowsInsert == 1;
    }

    @Override
    public Optional<Discount> update(Discount discount) throws DaoException {
        logger.error("Unavailable operation to entity <Discount>");
        throw new UnsupportedOperationException("Unavailable operation to entity <Discount>");
    }

    @Override
    public Optional<Discount> findDiscountByRate(int rate) throws DaoException {
        Optional<Discount> discount = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT + BY_RATE)) {
            statement.setInt(1, rate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    discount = Optional.of(createDiscountFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request findDiscountByRate from table hotel.discounts was failed");
            throw new DaoException("SQL request findDiscountByRate from table hotel.discounts was failed", e);
        }
        return discount;
    }

    private Discount createDiscountFromResultSet(ResultSet resultSet) throws SQLException {//TODO maybe make separate class
        Discount discount = new Discount();
        //discount.setEntityId(resultSet.getLong(DISCOUNT_ID));
        discount.setRate(resultSet.getInt(RATE));
        return discount;
    }
}
