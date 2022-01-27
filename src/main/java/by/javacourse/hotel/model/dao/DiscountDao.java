package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountDao extends BaseDao<Long, Discount> {
    List<Discount> findDiscountByRate(int rate) throws DaoException;

    Optional<Discount> findDiscountById(long rate) throws DaoException;

}
