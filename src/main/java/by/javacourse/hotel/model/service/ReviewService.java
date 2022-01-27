package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReviewService {
    List<Review> findReviewsByRoomId(long roomId) throws ServiceException;

    List<Review> findReviewsByDateRange(Map<String, String> parameters) throws ServiceException;

    List<Review> findAllReviews() throws ServiceException;

    List<Review> findReviewsByUserId(long userId) throws ServiceException;

    List<Review> findReviewsByUserIdFromDate(long userId, LocalDate date) throws ServiceException;

    boolean createReview(Map<String, String> reviewData, long roomId) throws ServiceException;

    Optional<Review> findReviewById(String reviewId) throws ServiceException;

    boolean updateHidden(long reviewId, boolean oldHiddenMark) throws ServiceException;
}
