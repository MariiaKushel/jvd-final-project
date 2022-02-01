package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code ReviewService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.Review}
 */
public interface ReviewService {

    /**
     * Find review by room id
     * @param roomId - room id
     * @return review list or empty list if review not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Review> findReviewsByRoomId(long roomId) throws ServiceException;

    /**
     * Find review by date range
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return review list or empty list if review not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Review> findReviewsByDateRange(Map<String, String> parameters) throws ServiceException;

    /**
     * Find all review
     * @return review list or empty list if review not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Review> findAllReviews() throws ServiceException;

    /**
     * Find review by user id
     * @param userId - user id
     * @return review list or empty list if review not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Review> findReviewsByUserId(long userId) throws ServiceException;

    /**
     * Find review by user id from concrete date
     * @param userId - user id
     * @param userId - from which date should make search
     * @return review list or empty list if review not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Review> findReviewsByUserIdFromDate(long userId, LocalDate date) throws ServiceException;

    /**
     * Create review
     * @param reviewData - map with review data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @param roomId - room id
     * @return true - if review was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createReview(Map<String, String> reviewData, long roomId) throws ServiceException;

    /**
     * Find review by id
     * @param reviewId - review id
     * @return an Optional describing review, or an empty Optional if review not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<Review> findReviewById(String reviewId) throws ServiceException;

    /**
     * Update review hidden mark
     * @param oldHiddenMark - old hidden mark which should change
     * @param reviewId - review id
     * @return true - if review was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updateHidden(long reviewId, boolean oldHiddenMark) throws ServiceException;
}
