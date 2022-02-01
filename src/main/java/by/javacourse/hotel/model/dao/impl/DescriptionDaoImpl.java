package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.DescriptionMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DescriptionDaoImpl implements DescriptionDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_DESCRIPTION = """
            INSERT INTO hotel.descriptions (description_id, description_ru, description_en)
            VALUES(?,?,?)""";
    private static final String SQL_UPDATE_DESCRIPTION = """
            UPDATE hotel.descriptions SET description_ru=?, description_en=?
            WHERE description_id=?""";
    private static final String SQL_SELECT_DESCRIPTION_BY_ID = """
            SELECT description_id, description_ru, description_en
            FROM hotel.descriptions
            WHERE description_id=? LIMIT 1""";

    @Override
    public List<Description> findAll() throws DaoException {
        logger.error("Unavailable operation to entity <Description>");
        throw new UnsupportedOperationException("Unavailable operation to entity <Description>");
    }

    @Override
    public boolean delete(Description description) throws DaoException {
        logger.error("Unavailable operation to entity <Description>");
        throw new UnsupportedOperationException("Unavailable operation to entity <Description>");
    }

    @Override
    public boolean create(Description description) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DESCRIPTION)) {
            statement.setLong(1, description.getEntityId());
            statement.setString(2, description.getDescriptionRu());
            statement.setString(3, description.getDescriptionEn());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request create from table hotel.descriptions was failed", e);
        }
        return rows == 1;
    }

    @Override
    public Optional<Description> findEntityById(Long descriptionId) throws DaoException {
        Optional<Description> description = Optional.empty();
        Mapper mapper = DescriptionMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DESCRIPTION_BY_ID)) {
            statement.setLong(1, descriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                description = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findEntityById from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request findEntityById from table hotel.descriptions was failed", e);
        }
        return description;
    }

    @Override
    public boolean update(Description description) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DESCRIPTION)) {
            statement.setString(1, description.getDescriptionRu());
            statement.setString(2, description.getDescriptionEn());
            statement.setLong(3, description.getEntityId());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request update from table hotel.descriptions was failed", e);
        }
        return rows == 1;
    }
}
