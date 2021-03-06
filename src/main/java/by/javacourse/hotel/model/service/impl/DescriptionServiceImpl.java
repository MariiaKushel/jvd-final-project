package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.service.DescriptionService;
import by.javacourse.hotel.validator.DescriptionValidator;
import by.javacourse.hotel.validator.ReviewValidator;
import by.javacourse.hotel.validator.impl.DescriptionValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class DescriptionServiceImpl implements DescriptionService {
    static Logger logger = LogManager.getLogger();

    private DaoProvider provider = DaoProvider.getInstance();
    private DescriptionDao descriptionDao = provider.getDescriptionDao();

    @Override
    public Optional<Description> findDescriptionByRoomId(long roomId) throws ServiceException {
        Optional<Description> description = Optional.empty();
        long descriptionId = roomId;
        try {
            description = descriptionDao.findEntityById(descriptionId);
        } catch (DaoException e) {
            logger.error("Try to findDescriptionByRoomId was failed " + e);
            throw new ServiceException("Try to findDescriptionByRoomId was failed", e);
        }
        return description;
    }

    @Override
    public boolean updateDescription(Map<String, String> descriptionData) throws ServiceException {
        boolean result = true;
        DescriptionValidator validator = DescriptionValidatorImpl.getInstance();
        if (!validator.validateDescriptionData(descriptionData)) {
            logger.info("not valid data");
            result = false;
            return result;
        }

        String descriptionRu = descriptionData.get(DESCRIPTION_RU_SES);
        String descriptionEn = descriptionData.get(DESCRIPTION_EN_SES);
        Description.Builder builder = Description.newBuilder()
                .setDescriptionRu(descriptionRu)
                .setDescriptionEn(descriptionEn);
        try {
            String tempDescriptionId = descriptionData.get(DESCRIPTION_ID_SES);
            Long descriptionId;
            if (tempDescriptionId == null) {
                descriptionId = Long.parseLong(descriptionData.get(ROOM_ID_SES));
                Description description = builder.setEntityId(descriptionId).build();
                result = descriptionDao.create(description);
            } else {
                descriptionId = Long.parseLong(tempDescriptionId);
                Description description = builder.setEntityId(descriptionId).build();
                result = descriptionDao.update(description);
            }
        } catch (NumberFormatException e) {
            logger.info("Not valid description id");
            result = false;
        } catch (DaoException e) {
            logger.error("Try to updateDescription was failed " + e);
            throw new ServiceException("Try to updateDescription was failed", e);
        }
        return result;
    }
}
