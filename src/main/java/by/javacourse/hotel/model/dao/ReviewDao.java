package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReviewDao extends BaseDao<Long, Review> {

    List<Review> findReviewByRoomId(long reviewId) throws DaoException;

    List<Review> findReviewByDateRange(LocalDate from, LocalDate to) throws DaoException;

    List<Review> findReviewByUserId(long userId) throws DaoException;

    List<Review> findReviewByUserIdFromDate(long userId, LocalDate date) throws DaoException;

    Optional<Review> findReviewById(long reviewId) throws DaoException;

    boolean update1(Review review) throws DaoException;

}
