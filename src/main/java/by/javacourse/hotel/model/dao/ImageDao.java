package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ImageDao extends BaseDao<Long, Image> {

    Optional<Image> findImageById(long imageId) throws DaoException;

    List<Image> findImageByRoomId(long roomId) throws DaoException;

    Optional<Image> findFirstImageByRoomId(long roomId) throws DaoException;

    List<Image> findPreviewByVisibleRoom() throws DaoException;
}
