package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomOrderDao;
import by.javacourse.hotel.model.service.RoomOrderService;
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
        RoomOrder order = RoomOrder.newBuilder()
                .setRoomId(Long.parseLong(orderData.get(ROOM_ID)))
                .setUserId(Long.parseLong(orderData.get(CURRENT_USER_ID)))
                .setDate(LocalDate.parse(orderData.get(TODAY)))
                .setFrom(LocalDate.parse(orderData.get(DATE_FROM)))
                .setTo(LocalDate.parse(orderData.get(DATE_TO)))
                .setAmount(new BigDecimal(orderData.get(TOTAL_AMOUNT)))
                .setPrepayment(Boolean.parseBoolean(orderData.get(PREPAYMENT)))
                .build();

        DaoProvider provider = DaoProvider.getInstance();
        RoomOrderDao roomOrderDao = provider.getRoomOrderDao();
        boolean result = false;
        try {
            result = roomOrderDao.create(order);
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
    public BigDecimal countBaseAmount(int days, String roomPrice){
        return new BigDecimal(roomPrice).multiply(new BigDecimal(days));
    }

    @Override
    public BigDecimal countTotalAmount(int days, String roomPrice, String discount) {
        BigDecimal dis = (new BigDecimal(100).subtract(new BigDecimal(discount))).divide(new BigDecimal(100));
        BigDecimal amount = countBaseAmount(days, roomPrice);
        return amount.multiply(dis);
    }
}
