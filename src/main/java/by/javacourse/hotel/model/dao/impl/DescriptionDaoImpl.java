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

    private static final String SQL_SELECT_DESCRIPTION = """
            SELECT description_id, description_ru, description_en
            FROM hotel.descriptions""";

    private static final String BY_ID = " WHERE description_id=? LIMIT 1";

    private static final String SQL_INSERT_DESCRIPTION = """
            INSERT INTO hotel.descriptions (description_id, description_ru, description_en)
            VALUES(?,?,?)""";

    private static final String SQL_UPDATE_DESCRIPTION = """
            UPDATE hotel.descriptions SET description_ru=?, description_en=?
            WHERE description_id=?""";

    @Override
    public List<Description> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Description description) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Description description) throws DaoException {
        int insertedRows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DESCRIPTION)) {
            statement.setLong(1, description.getEntityId());
            statement.setString(2, description.getDescriptionRu());
            statement.setString(3, description.getDescriptionEn());
            insertedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request create from table hotel.descriptions was failed", e);
        }
        return insertedRows == 1;
    }

    @Override
    public Optional<Description> update(Description description) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Description> findDescriptionById(long descriptionId) throws DaoException {
        Optional<Description> description = Optional.empty();
        Mapper mapper = DescriptionMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DESCRIPTION + BY_ID)) {
            statement.setLong(1, descriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                description = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findDescriptionById from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request findDescriptionById from table hotel.descriptions was failed", e);
        }
        return description;
    }

    @Override
    public boolean update1(Description description) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DESCRIPTION)) {
            statement.setString(1, description.getDescriptionRu());
            statement.setString(2, description.getDescriptionEn());
            statement.setLong(3, description.getEntityId());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.descriptions was failed" + e);
            throw new DaoException("SQL request update1 from table hotel.descriptions was failed", e);
        }
        return rowsUpdated == 1;
    }
}
