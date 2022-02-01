package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * {@code DescriptionDao} class implements functional of {@link BaseDao}
 */
public interface ImageDao extends BaseDao<Long, Image> {

    /**
     * Find image by room id
     * @param roomId - room id
     * @return list of image or empty list if image not found
     * @throws DaoException - if request from database was failed
     */
    List<Image> findImageByRoomId(long roomId) throws DaoException;

    /**
     * Find image which have preview mark by all rooms which have visible mark
     * @return list of image or empty list if image not found
     * @throws DaoException - if request from database was failed
     */
    List<Image> findPreviewByVisibleRoom() throws DaoException;

    /**
     * Update image preview mark
     * @param newPreviewId - image id new preview
     * @param roomId       - room id
     * @return true - if preview mark was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean changePreview(long newPreviewId, long roomId) throws DaoException;
}
