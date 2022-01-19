package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomOrderDao;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.validator.RoomOrderValidator;
import by.javacourse.hotel.validator.impl.RoomOrderValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

public class RoomOrderServiceImpl implements RoomOrderService {
    static Logger logger = LogManager.getLogger();

    @Override
    public boolean createOrder(Map<String, String> orderData) throws ServiceException {
        boolean result = false;

        DaoProvider provider = DaoProvider.getInstance();
        UserDao userDao = provider.getUserDao();

        long userId = Long.parseLong(orderData.get(CURRENT_USER_ID));
        try{
            BigDecimal balance = userDao.findBalanceByUserId(userId);
            RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();
            if (!validator.validateOrderData(orderData, balance)){
                return result;
            }
        } catch (DaoException e) {
            logger.error("Try to createOrder was failed " + e);
            throw new ServiceException("Try to createOrder  was failed", e);
        }

        long roomId = Long.parseLong(orderData.get(ROOM_ID));
        LocalDate date = LocalDate.parse(orderData.get(ORDER_DATE));
        LocalDate from = LocalDate.parse(orderData.get(DATE_FROM));
        LocalDate to = LocalDate.parse(orderData.get(DATE_TO));
        BigDecimal amount = new BigDecimal(orderData.get(TOTAL_AMOUNT));
        boolean prepayment = orderData.get(PREPAYMENT) != null ? true : false;
        int days = Integer.parseInt(orderData.get(DAYS));

        RoomOrder order = RoomOrder.newBuilder()
                .setRoomId(roomId)
                .setUserId(userId)
                .setDate(date)
                .setFrom(from)
                .setTo(to)
                .setAmount(amount)
                .setPrepayment(prepayment)
                .build();

        RoomOrderDao roomOrderDao = provider.getRoomOrderDao();
        try {
            result = roomOrderDao.createOrderWithRoomStates(order, days);
        } catch (DaoException e) {
            logger.error("Try to createOrder was failed " + e);
            throw new ServiceException("Try to createOrder  was failed", e);
        }
        return result;
    }

    @Override
    public int countDays(String from, String to) {
        LocalDate dateFrom = LocalDate.parse(from);
        LocalDate dateTo = LocalDate.parse(to);
        Period period = Period.between(dateFrom, dateTo);
        int days = period.getDays();
        return days;
    }

    @Override
    public BigDecimal countBaseAmount(int days, String roomPrice) {
        return new BigDecimal(roomPrice).multiply(new BigDecimal(days));
    }

    @Override
    public BigDecimal countTotalAmount(int days, String roomPrice, int discount) {
        BigDecimal dis = (new BigDecimal(100).subtract(new BigDecimal(discount))).divide(new BigDecimal(100));
        BigDecimal amount = countBaseAmount(days, roomPrice);
        return amount.multiply(dis);
    }
}
