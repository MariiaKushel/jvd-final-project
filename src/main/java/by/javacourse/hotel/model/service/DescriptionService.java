package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.ServiceException;

import java.util.Optional;

public interface DescriptionService {
    Optional<Description> findDescriptionByRoomId(long roomId) throws ServiceException;
}
