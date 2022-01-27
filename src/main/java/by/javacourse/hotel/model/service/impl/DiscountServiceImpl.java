package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Discount;
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
        String tempRate = parameters.get(RATE_ATR);
        DiscountValidator validator = DiscountValidatorImpl.getInstance();
        if (!validator.validateRate(tempRate)) {
            parameters.put(WRONG_RATE_ATR, DiscountValidator.WRONG_DATA_MARKER);
        }
        try {
            if (!tempRate.isEmpty()) {
                int rate = Integer.parseInt(tempRate);
                discounts = discountDao.findDiscountByRate(rate);
            }else{
                discounts = discountDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to findDiscountByRate was failed " + e);
            throw new ServiceException("Try to findDiscountByRate was failed", e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findDiscountById(long discountId) throws ServiceException {
        Optional<Discount> discount = Optional.empty();
        try {
            discount = discountDao.findDiscountById(discountId);
        } catch (DaoException e) {
            logger.error("Try to findDiscountById was failed " + e);
            throw new ServiceException("Try to findDiscountById was failed", e);
        }
        return discount;
    }
}
