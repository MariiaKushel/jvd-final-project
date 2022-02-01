package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * {@code BaseDao} interface represent common functional to dao classes
 */
public interface BaseDao<K, T extends Entity> {

    /**
     * Find all entity from database
     * @return entity list or empty list if table is empty
     * @throws DaoException - if request from database was failed
     */
    List<T> findAll() throws DaoException;

    /**
     * Delete entity from database
     * @param t - entity extends {@link Entity}
     * @return true - if entity was deleted and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean delete(T t) throws DaoException;

    /**
     * Create new entity in database
     * @param t - entity extends {@link Entity}
     * @return true - if entity was created and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean create(T t) throws DaoException;

    /**
     * Update entity in database
     * @param t - entity extends {@link Entity}
     * @return true - if entity was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean update(T t) throws DaoException;

    /**
     * Find entity from database by id
     * @param id - entity id
     * @return an Optional describing entity, or an empty Optional if entity not found
     * @throws DaoException - if request from database was failed
     */
    Optional<T> findEntityById(K id) throws DaoException;
}
