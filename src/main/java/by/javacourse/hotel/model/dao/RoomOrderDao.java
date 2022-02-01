package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code RoomOrderDao} class implements functional of {@link BaseDao}
 */
public interface RoomOrderDao extends BaseDao<Long, RoomOrder> {

    /**
     * Create new order, new daily room state and update user balance by that order
     * @param roomOrder - order id
     * @param days - number of days into order
     * @return true - if roomOrder, daily room state and user balance was create and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean createOrderWithRoomStates(RoomOrder roomOrder, int days) throws DaoException;

    /**
     * Find order by prepayment
     * @param prepayment - prepayment mark
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<RoomOrder> findOrderByPrepayment(boolean prepayment) throws DaoException;

    /**
     * Find order by order status
     * @param status - order status
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<RoomOrder> findOrderByStatus(RoomOrder.Status status) throws DaoException;

    /**
     * Find order by date range
     * @param from - low border of range
     * @param to - upper border of range
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<RoomOrder> findOrderByDateRange(LocalDate from, LocalDate to) throws DaoException;

    /**
     * Find order by user id
     * @param userId - user id
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<RoomOrder> findOrderByUserId(long userId) throws DaoException;

    /**
     * Find concrete number of last orders by user id
     * @param userId - user id
     * @param last - number of last orders which need find
     * @return list of order or empty list if order not found
     * @throws DaoException - if request from database was failed
     */
    List<RoomOrder> findOrderByUserIdLast(long userId, int last) throws DaoException;

}
