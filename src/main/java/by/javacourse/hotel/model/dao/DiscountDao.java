package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.Discount;

import java.util.List;
import java.util.Optional;

/**
 * {@code DiscountDao} class implements functional of {@link BaseDao}
 */
public interface DiscountDao extends BaseDao<Long, Discount> {

    /**
     * Find discount by rate
     * @param rate - rate value
     * @return list of discount or empty list if discount not found
     * @throws DaoException - if request from database was failed
     */
    List<Discount> findDiscountByRate(int rate) throws DaoException;

    /**
     * Find discount by user id
     * @param userId - user id
     * @return an Optional describing discount, or an empty Optional if discount not found
     * @throws DaoException - if request from database was failed
     */
    Optional<Discount> findDiscountByUserId(long userId) throws DaoException;

}
