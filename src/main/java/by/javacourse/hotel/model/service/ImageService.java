package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * {@code ImageService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.Image}
 */
public interface ImageService {

    /**
     * Create image
     * @param image - image as byte array
     * @param roomId - room id
     * @return true - if image was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createImage(byte[] image, String roomId) throws ServiceException;

    /**
     * Find image by room id
     * @param roomId - room id
     * @return image list or empty list if image not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Image> findImagesByRoomId(long roomId) throws ServiceException;

    /**
     * Change preview mark by room
     * @param newPreviewId - new priview image id
     * @param roomId - room id
     * @return true - if image was updated and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean changePreview(String newPreviewId, String roomId) throws ServiceException;
}
