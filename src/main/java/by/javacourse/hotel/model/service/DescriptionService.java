package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface DescriptionService {
    Optional<Description> findDescriptionByRoomId(long roomId) throws ServiceException;

    boolean updateDescription(Map<String, String> descriptionData) throws ServiceException;

    boolean createDescription(Map<String, String> descriptionData) throws ServiceException;
}
