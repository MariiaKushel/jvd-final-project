package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;

public interface ReviewService {
    List<Review> findReviewsByRoomId(long roomId) throws ServiceException;
}
