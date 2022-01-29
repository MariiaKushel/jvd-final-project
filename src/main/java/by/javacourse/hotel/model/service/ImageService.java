package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    boolean addNewImage(byte[] image, String roomId) throws ServiceException;

    List<Image> findImagesByRoomId(long roomId) throws ServiceException;

    Optional<Image> findPreviewImageByRoomId(long roomId) throws ServiceException;

    List<Image> findPreviewByVisibleRoom() throws ServiceException;

    boolean changePreview(String newPreviewId, String roomId) throws ServiceException;
}
