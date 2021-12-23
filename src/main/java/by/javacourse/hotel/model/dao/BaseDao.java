package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    static Logger logger = LogManager.getLogger();

    List<T> findAll() throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean create(T t) throws DaoException;

    Optional<T> update(T t) throws DaoException;

    default void close(Statement statment) { //TODO if not use - remove
        try {
            if (statment != null) {
                statment.close();
            }
        } catch (SQLException e) {
            logger.error("Try to close statment was failed");
        }
    }

    default void close(Connection connection) { //TODO if not use - remove
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Try to close connection was failed");
        }
    }

}
