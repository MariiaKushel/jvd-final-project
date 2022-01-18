package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.service.DescriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class DescriptionServiceImpl implements DescriptionService {
    static Logger logger = LogManager.getLogger();
    private DaoProvider provider = DaoProvider.getInstance();
    private DescriptionDao descriptionDao = provider.getDescriptionDao();

    @Override
    public Optional<Description> findDescriptionByRoomId(long roomId) throws ServiceException {
        Optional<Description> description = Optional.empty();
        long descriptionId = roomId;
        try {
            description = descriptionDao.findDescriptionById(descriptionId);
        } catch (DaoException e) {
            logger.error("Try to findDescriptionByRoomId was failed " + e);
            throw new ServiceException("Try to findDescriptionByRoomId was failed", e);
        }
        return description;
    }
}
