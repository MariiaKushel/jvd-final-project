package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;

import java.util.Optional;

public interface DescriptionDao extends BaseDao<Long, Description> {

    Optional<Description> findDescriptionById(long descriptionId) throws DaoException;

}
