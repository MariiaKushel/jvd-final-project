package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.Discount;

import java.util.Optional;

public interface DiscountDao extends BaseDao<Long, Discount> {
    Optional<Discount> findDiscountByRate(int rate) throws DaoException;
}
