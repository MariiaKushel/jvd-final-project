package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;

import java.math.BigDecimal;

public interface RoomOrderDao extends BaseDao<Long, RoomOrder> {

    boolean createOrderWithRoomStates(RoomOrder roomOrder, int days) throws DaoException;
}
