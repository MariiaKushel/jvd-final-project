package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface RoomOrderService {
    boolean createOrder(Map<String, String> orderData) throws ServiceException;

    int countDays(String from, String to) ;

    BigDecimal countBaseAmount(int days, String roomPrice) ;

    BigDecimal countTotalAmount(int days, String roomPrice, int discount) ;
}
