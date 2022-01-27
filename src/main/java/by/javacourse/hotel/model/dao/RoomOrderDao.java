package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomOrderDao extends BaseDao<Long, RoomOrder> {

    boolean createOrderWithRoomStates(RoomOrder roomOrder, int days) throws DaoException;

    List<RoomOrder> findOrderByPrepayment(boolean prepayment) throws DaoException;

    List<RoomOrder> findOrderByStatus(RoomOrder.Status status) throws DaoException;

    List<RoomOrder> findOrderByDateRange(LocalDate from, LocalDate to) throws DaoException;

    List<RoomOrder> findOrderByUserId(long userId) throws DaoException;

    List<RoomOrder> findOrderByUserIdLast(long userId, int last) throws DaoException;

    Optional<RoomOrder> findOrderById(long orderId) throws DaoException;

    boolean update1(RoomOrder roomOrder) throws DaoException;

}
