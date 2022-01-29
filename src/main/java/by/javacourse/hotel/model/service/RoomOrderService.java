package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomOrderService {

    boolean createOrder(Map<String, String> orderData) throws ServiceException;

    int countDays(String from, String to) throws ServiceException;

    BigDecimal countBaseAmount(int days, BigDecimal roomPrice) throws ServiceException;

    BigDecimal countTotalAmount(int days, BigDecimal roomPrice, int discount) throws ServiceException;

    List<RoomOrder> findOrderByPrepayment(Map<String, String> parameters) throws ServiceException;

    List<RoomOrder> findOrderByStatus(Map<String, String> parameters) throws ServiceException;

    List<RoomOrder> findOrderByDateRange(Map<String, String> parameters) throws ServiceException;

    List<RoomOrder> findAllOrders() throws ServiceException;

    List<RoomOrder> findOrderByUserId(long userId) throws ServiceException;

    List<RoomOrder> findOrderByUserIdLast(long userId, Map<String, String> parameters) throws ServiceException;

    Optional<RoomOrder> findOrderById(Map<String, String> parameters) throws ServiceException;

    boolean updateStatus(String role, RoomOrder.Status newStatus, RoomOrder order) throws ServiceException;

    Map<Long, Boolean> createСanBeCanceledMap(List<RoomOrder> orders);

    Map<Long, Boolean> createСanBeUpdatedMap(List<RoomOrder> orders);

    List<RoomOrder.Status> findAvailableStatuses(RoomOrder.Status status);

}
