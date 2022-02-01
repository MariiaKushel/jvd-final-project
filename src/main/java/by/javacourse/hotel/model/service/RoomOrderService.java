package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code RoomOrderService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.RoomOrder}
 */
public interface RoomOrderService {

    /**
     * Create order
     * @param orderData - map with order data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if order was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createOrder(Map<String, String> orderData) throws ServiceException;

    /**
     * Count period size
     * @param from - low border of range
     * @param to - upper border of range
     * @return number of days into period
     * @throws ServiceException - if data range wrong
     */
    int countDays(String from, String to) throws ServiceException;

    /**
     * Count amount of order without discount
     * @param days - days in order
     * @param roomPrice - room price
     * @return amount of order without discount
     * @throws ServiceException - if amount less 0
     */
    BigDecimal countBaseAmount(int days, BigDecimal roomPrice) throws ServiceException;

    /**
     * Count amount of order with discount
     * @param days - days in order
     * @param roomPrice - room price
     * @param discount - user discount rate
     * @return amount of order with discount
     * @throws ServiceException - if amount less 0
     */
    BigDecimal countTotalAmount(int days, BigDecimal roomPrice, int discount) throws ServiceException;

    /**
     * Find order by status
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findOrderByStatus(Map<String, String> parameters) throws ServiceException;

    /**
     * Find order by date range
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findOrderByDateRange(Map<String, String> parameters) throws ServiceException;

    /**
     * Find order by prepayment
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findOrderByPrepayment(Map<String, String> parameters) throws ServiceException;

    /**
     * Find all orders
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findAllOrders() throws ServiceException;

    /**
     * Find order by user id
     * @param userId - user id
     * @return order list or empty list if order not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findOrderByUserId(long userId) throws ServiceException;

    /**
     * Find concrete number last orders by user id
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @param userId - user id
     * @return order list or empty list if order not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder> findOrderByUserIdLast(long userId, Map<String, String> parameters) throws ServiceException;

    /**
     * Find order by id
     * @param orderId - order id
     * @return an Optional describing order, or an empty Optional if order not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<RoomOrder> findOrderById(String orderId) throws ServiceException;

    /**
     * Update order status
     * @param role - user role
     * @param newStatus - value of new order status
     * @param order - {@link RoomOrder} object
     * @return true - if order was updated and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updateStatus(String role, RoomOrder.Status newStatus, RoomOrder order) throws ServiceException;

    /**
     * Create map of order id and mark "can be cancel"
     * @param orders - order list
     * @return map represent relations between order id and mark "can be cancel"
     */
    Map<Long, Boolean> createСanBeCanceledMap(List<RoomOrder> orders);

    /**
     * Create map of order id and mark "can be update"
     * @param orders - order list
     * @return map represent relations between order id and mark "can be update"
     */
    Map<Long, Boolean> createСanBeUpdatedMap(List<RoomOrder> orders);

    /**
     * Form list available order statuses for current status
     * @param status - current status
     * @return order status list or empty list if current order status have not available statuses
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<RoomOrder.Status> findAvailableStatuses(RoomOrder.Status status);

}
