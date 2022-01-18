package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.DescriptionMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DescriptionDaoImpl implements DescriptionDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_DESCRIPTION = """
            SELECT description_id, description_ru, description_en
            FROM hotel.descriptions""";

    private static final String BY_ID = " WHERE description_id=? LIMIT 1";

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
        return false;
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
}
