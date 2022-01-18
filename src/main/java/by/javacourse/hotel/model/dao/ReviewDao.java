package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;

import java.util.List;

public interface ReviewDao extends BaseDao<Long, Review> {

    List<Review> findReviewsByRoomId(long reviewId) throws DaoException;

}
