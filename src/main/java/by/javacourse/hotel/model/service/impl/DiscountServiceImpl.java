package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.DiscountDao;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.validator.DiscountValidator;
import by.javacourse.hotel.validator.impl.DiscountValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.RequestAttribute.RATE_ATR;
import static by.javacourse.hotel.controller.command.RequestAttribute.WRONG_RATE_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class DiscountServiceImpl implements DiscountService {
    static Logger logger = LogManager.getLogger();
    private DaoProvider provider = DaoProvider.getInstance();
    private DiscountDao discountDao = provider.getDiscountDao();

    @Override
    public List<Discount> findAllDiscount() throws ServiceException {
        List<Discount> discounts = new ArrayList<>();
        try {
            discounts = discountDao.findAll();
        } catch (DaoException e) {
            logger.error("Try to findAllDiscount was failed " + e);
            throw new ServiceException("Try to findAllDiscount was failed", e);
        }
        return discounts;
    }

    @Override
    public List<Discount> findDiscountByRate(Map<String, String> parameters) throws ServiceException {
        List<Discount> discounts = new ArrayList<>();

        try {
            String tempRate = parameters.get(RATE_ATR);
            if (!tempRate.isEmpty()) {
                DiscountValidator validator = DiscountValidatorImpl.getInstance();
                if (!validator.validateRate(tempRate)) {
                    parameters.put(WRONG_RATE_ATR, DiscountValidator.WRONG_DATA_MARKER);
                }
                int rate = Integer.parseInt(tempRate);
                discounts = discountDao.findDiscountByRate(rate);
            } else {
                discounts = discountDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to findDiscountByRate was failed " + e);
            throw new ServiceException("Try to findDiscountByRate was failed", e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findDiscountById(String discountId) throws ServiceException {
        Optional<Discount> discount = Optional.empty();
        try {
            long disId = Long.parseLong(discountId);
            discount = discountDao.findDiscountById(disId);
        } catch (NumberFormatException e) {
            logger.error("Not valid discount id");
            return discount;
        } catch (DaoException e) {
            logger.error("Try to findDiscountById was failed " + e);
            throw new ServiceException("Try to findDiscountById was failed", e);
        }
        return discount;
    }

    @Override
    public boolean updateDiscount(Map<String, String> parameters) throws ServiceException {
        boolean result = false;
        String discountId = parameters.get(DISCOUNT_ID_SES);
        String rate = parameters.get(RATE_SES);
        try {
            DiscountValidator validator = DiscountValidatorImpl.getInstance();
            if (!validator.validateRate(rate)) {
                logger.error("Not valid rate");
                parameters.put(WRONG_RATE_SES, DiscountValidator.WRONG_DATA_MARKER);
                return result;
            }
            Discount discount = Discount.newBuilder()
                    .setEntityId(Long.parseLong(discountId))
                    .setRate(Integer.parseInt(rate))
                    .build();
            result = discountDao.update1(discount);
        } catch (NumberFormatException e) {
            logger.error("Not valid discount id");
            return result;
        } catch (DaoException e) {
            logger.error("Try to updateDiscount was failed " + e);
            throw new ServiceException("Try to updateDiscount was failed", e);
        }
        return result;
    }

    @Override
    public boolean createDiscount(Map<String, String> parameters) throws ServiceException {
        boolean result = false;
        String rate = parameters.get(RATE_SES);
        try {
            DiscountValidator validator = DiscountValidatorImpl.getInstance();
            if (!validator.validateRate(rate)) {
                logger.error("Not valid rate");
                parameters.put(WRONG_RATE_SES, DiscountValidator.WRONG_DATA_MARKER);
                return result;
            }
            Discount discount = Discount.newBuilder()
                    .setRate(Integer.parseInt(rate))
                    .build();
            result = discountDao.create(discount);
        } catch (DaoException e) {
            logger.error("Try to createDiscount was failed " + e);
            throw new ServiceException("Try to createDiscount was failed", e);
        }
        return result;
    }

    @Override
    public boolean removeDiscount(Discount discount) throws ServiceException {
        boolean result = false;
        if (discount.getEntityId() == User.DEFAULT_DISCOUNT_ID) {
            logger.error("Impossible remove default discount");
            throw new ServiceException("Impossible remove default discount");
        }
        try {
            result = discountDao.delete(discount);
        } catch (DaoException e) {
            logger.error("Try to removeDiscount was failed " + e);
            throw new ServiceException("Try to removeDiscount was failed", e);
        }
        return result;
    }
}
