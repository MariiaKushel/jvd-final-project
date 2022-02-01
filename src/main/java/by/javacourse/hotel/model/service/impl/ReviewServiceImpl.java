package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.ReviewDao;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.validator.ReviewValidator;
import by.javacourse.hotel.validator.impl.ReviewValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class ReviewServiceImpl implements ReviewService {
    static Logger logger = LogManager.getLogger();

    private static final String DEFAULT_DATE_FROM = "2020-01-01";
    private DaoProvider provider = DaoProvider.getInstance();
    private ReviewDao reviewDao = provider.getReviewDao();

    @Override
    public List<Review> findReviewsByRoomId(long roomId) throws ServiceException {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewDao.findReviewByRoomId(roomId);
        } catch (DaoException e) {
            logger.error("Try to findReviewsByRoomId was failed " + e);
            throw new ServiceException("Try to findReviewsByRoomId was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewsByDateRange(Map<String, String> parameters) throws ServiceException {
        List<Review> reviews = new ArrayList<>();

        String tempFrom = parameters.get(DATE_FROM_ATR);
        String tempTo = parameters.get(DATE_TO_ATR);
        tempFrom = tempFrom.isEmpty()
                ? DEFAULT_DATE_FROM
                : tempFrom;
        tempTo = tempTo.isEmpty()
                ? LocalDate.now().toString()
                : tempTo;
        ReviewValidator validator = ReviewValidatorImpl.getInstance();
        if (!validator.validateDateRange(tempFrom, tempTo)) {
            parameters.put(WRONG_DATE_RANGE_ATR, ReviewValidator.WRONG_DATA_MARKER);
            return reviews;
        }
        try {
            LocalDate from = LocalDate.parse(tempFrom);
            LocalDate to = LocalDate.parse(tempTo);
            reviews = reviewDao.findReviewByDateRange(from, to);
        } catch (DaoException e) {
            logger.error("Try to findReviewsByDateRange was failed " + e);
            throw new ServiceException("Try to findReviewsByDateRange was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findAllReviews() throws ServiceException {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewDao.findAll();
        } catch (DaoException e) {
            logger.error("Try to findAllReviews was failed " + e);
            throw new ServiceException("Try to findAllReviews was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewsByUserId(long userId) throws ServiceException {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewDao.findReviewByUserId(userId);
        } catch (DaoException e) {
            logger.error("Try to findReviewsByUserId was failed " + e);
            throw new ServiceException("Try to findReviewsByUserId was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewsByUserIdFromDate(long userId, LocalDate date) throws ServiceException {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewDao.findReviewByUserIdFromDate(userId, date);
        } catch (DaoException e) {
            logger.error("Try to findReviewsByUserIdFromDate was failed " + e);
            throw new ServiceException("Try to findReviewsByUserIdFromDate was failed", e);
        }
        return reviews;
    }

    @Override
    public boolean createReview(Map<String, String> reviewData, long roomId) throws ServiceException {
        boolean isCreate = false;

        String mark = reviewData.get(MARK_SES);
        String content = reviewData.get(CONTENT_SES);
        ReviewValidator validator = ReviewValidatorImpl.getInstance();
        if (!validator.validateContent(content) && mark.isEmpty()) {
            logger.info("Not valid mark or content");
            reviewData.put(WRONG_CONTENT_SES, ReviewValidator.WRONG_DATA_MARKER);
            return isCreate;
        }
        Review.Builder builder = Review.newBuilder()
                .setEntityId(Long.parseLong(reviewData.get(ORDER_ID_SES)))
                .setDate(LocalDate.parse(reviewData.get(DATE_SES)))
                .setHidden(true);
        if (!mark.isEmpty()) {
            builder.setRoomMark(Integer.parseInt(mark));
        }
        if (!content.isEmpty()) {
            builder.setReviewContent(content);
        }
        Review review = builder.build();
        try {
            isCreate = reviewDao.create(review);
            if (isCreate) {
                RoomDao roomDao = provider.getRoomDao();
                roomDao.refreshRating(roomId);
            }
        } catch (DaoException e) {
            logger.error("Try to createReview was failed " + e);
            throw new ServiceException("Try to createReview was failed", e);
        }
        return isCreate;
    }

    @Override
    public Optional<Review> findReviewById(String reviewId) throws ServiceException {
        Optional<Review> review = Optional.empty();
        try {
            long reviewIdL = Long.parseLong(reviewId);
            review = reviewDao.findEntityById(reviewIdL);
        } catch (NumberFormatException e) {
            logger.info("Not valid review id");
            return review;
        } catch (DaoException e) {
            logger.error("Try to findReviewById was failed " + e);
            throw new ServiceException("Try to findReviewById was failed", e);
        }
        return review;
    }

    @Override
    public boolean updateHidden(long reviewId,  boolean oldHiddenMark) throws ServiceException {
       boolean isUpdate = false;
        Review review = Review.newBuilder()
               .setEntityId(reviewId)
               .setHidden(!oldHiddenMark)
               .build();
        try {
            isUpdate = reviewDao.update(review);
        } catch (DaoException e) {
            logger.error("Try to updateHidden was failed " + e);
            throw new ServiceException("Try to updateHidden was failed", e);
        }
        return isUpdate;
    }
}
