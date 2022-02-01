package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

/**
 * {@code DescriptionService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.Description}
 */
public interface DescriptionService {

    /**
     * Find description by room id
     * @param roomId - room id which equals description id
     * @return an Optional description, or an empty Optional if description not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<Description> findDescriptionByRoomId(long roomId) throws ServiceException;

    /**
     * Update description in database
     * @param descriptionData - map with description data.
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if entity was updated and false - if was not
     * If some data is not valid, wrong data marker add into descriptionData as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updateDescription(Map<String, String> descriptionData) throws ServiceException;

}
