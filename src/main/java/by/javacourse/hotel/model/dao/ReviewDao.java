package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code ReviewDao} class implements functional of {@link BaseDao}
 */
public interface ReviewDao extends BaseDao<Long, Review> {

    /**
     * Find review by room id
     * @param roomId - room id
     * @return list of review or empty list if review not found
     * @throws DaoException - if request from database was failed
     */
    List<Review> findReviewByRoomId(long roomId) throws DaoException;

    /**
     * Find review by date range
     * @param from - low border of range
     * @param to - upper border of range
     * @return list of review or empty list if review not found
     * @throws DaoException - if request from database was failed
     */
    List<Review> findReviewByDateRange(LocalDate from, LocalDate to) throws DaoException;

    /**
     * Find review by user id
     * @param userId - user id
     * @return list of review or empty list if review not found
     * @throws DaoException - if request from database was failed
     */
    List<Review> findReviewByUserId(long userId) throws DaoException;

    /**
     * Find review by user id from concrete date
     * @param userId - user id
     * @param date - from which date should search
     * @return list of review or empty list if review not found
     * @throws DaoException - if request from database was failed
     */
    List<Review> findReviewByUserIdFromDate(long userId, LocalDate date) throws DaoException;
}
