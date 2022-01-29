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

public class DiscountDaoImpl implements DiscountDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_DISCOUNT =
            "INSERT INTO hotel.discounts (rate) VALUES (?)";

    private static final String SQL_SELECT_DISCOUNT =
            "SELECT discount_id, rate FROM hotel.discounts";

    private static final String BY_RATE =
            " WHERE rate=? LIMIT 1";

    private static final String BY_ID =
            " WHERE discount_id=? LIMIT 1";

    private static final String SQL_UPDATE_DISCOUNT = """
            UPDATE hotel.discounts SET rate=? WHERE discount_id=?""";

    private static final String SQL_DELETE_DISCOUNT = """
            DELETE FROM hotel.discounts WHERE discount_id=?""";

    @Override
    public List<Discount> findAll() throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_DISCOUNT)) {
            discounts = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.discounts was failed");
            throw new DaoException("SQL request findAll from table discounts.users was failed", e);
        }
        return discounts;
    }

    @Override
    public boolean delete(Discount discount) throws DaoException {
        int rowsDeleted = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DISCOUNT)) {
            statement.setLong(1, discount.getEntityId());
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request delete from table hotel.discounts was failed");
            throw new DaoException("SQL request delete from table hotel.discounts was failed", e);
        }
        return rowsDeleted == 1;
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
        return Optional.empty();
    }

    @Override
    public boolean update1(Discount discount) throws DaoException {
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
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT + BY_RATE)) {
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
    public Optional<Discount> findDiscountById(long discountId) throws DaoException {
        Optional<Discount> discount = Optional.empty();
        DiscountMapper mapper = DiscountMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DISCOUNT + BY_ID)) {
            statement.setLong(1, discountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                discount = mapper.retrieve(resultSet).stream().findFirst();
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
