package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.ReviewDao;
import by.javacourse.hotel.model.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    static Logger logger = LogManager.getLogger();
    private DaoProvider provider = DaoProvider.getInstance();
    private ReviewDao reviewDao = provider.getReviewDao();

    @Override
    public List<Review> findReviewsByRoomId(long roomId) throws ServiceException {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewDao.findReviewsByRoomId(roomId);
        } catch (DaoException e) {
            logger.error("Try to findReviewsByRoomId was failed " + e);
            throw new ServiceException("Try to findReviewsByRoomId was failed", e);
        }
        return reviews;
    }
}
