package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.ImageDao;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageServiceImpl implements ImageService {
    static Logger logger = LogManager.getLogger();

    private DaoProvider provider = DaoProvider.getInstance();
    private ImageDao imageDao = provider.getImageDao();

    @Override
    public boolean addNewImage(byte[] imageAsBytes, String roomId) throws ServiceException {
        boolean isCreate = false;
        Image image = Image.newBuilder()
                .setRoomId(Long.parseLong(roomId))
                .setImageContent(imageAsBytes)
                .build();
        try {
            isCreate = imageDao.create(image);
        } catch (NumberFormatException e) {
            logger.error("Not valid room id");
        } catch (DaoException e) {
            logger.error("Try to addNewImage was failed " + e);
            throw new ServiceException("Try to addNewImage was failed", e);
        }
        return isCreate;
    }

    @Override
    public List<Image> findImagesByRoomId(long roomId) throws ServiceException {
        List<Image> images = new ArrayList<>();
        try {
            images = imageDao.findImageByRoomId(roomId);
        } catch (DaoException e) {
            logger.error("Try to findImagesByRoomId was failed " + e);
            throw new ServiceException("Try to findImagesByRoomId was failed", e);
        }
        return images;
    }

    @Override
    public Optional<Image> findPreviewImageByRoomId(long roomId) throws ServiceException {
        Optional<Image> image = Optional.empty();
        try {
            image = imageDao.findFirstImageByRoomId(roomId);
        } catch (DaoException e) {
            logger.error("Try to findPreviewImageByRoomId was failed " + e);
            throw new ServiceException("Try to findPreviewImageByRoomId was failed", e);
        }
        return image;
    }

    @Override
    public List<Image> findPreviewByVisibleRoom() throws ServiceException {
        List<Image> images = new ArrayList<>();
        try {
            images = imageDao.findPreviewByVisibleRoom();
        } catch (DaoException e) {
            logger.error("Try to findPreviewByVisibleRoom was failed " + e);
            throw new ServiceException("Try to findPreviewByVisibleRoom was failed", e);
        }
        return images;
    }

    @Override
    public boolean changePreview(String newPreviewId, String roomId) throws ServiceException {
        boolean result = false;
        try {
            long newPreviewIdL = Long.parseLong(newPreviewId);
            long roomIdL = Long.parseLong(roomId);
            result = imageDao.changePreview(newPreviewIdL, roomIdL);
        } catch (NumberFormatException e) {
            logger.info("Not valid preview or room id");
        } catch (DaoException e) {
            logger.error("Try to changePreview was failed " + e);
            throw new ServiceException("Try to changePreview was failed", e);
        }
        return result;
    }
}
