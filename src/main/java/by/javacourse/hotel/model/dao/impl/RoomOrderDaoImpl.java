package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.ColumnName;
import by.javacourse.hotel.model.dao.RoomOrderDao;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RoomOrderDaoImpl implements RoomOrderDao {
    static Logger logger = LogManager.getLogger();

    private static final String STATE_BOOKED = "booked"; // FIXME maybe enum

    private static final String SQL_INSERT_ROOM_ORDER = """
            INSERT INTO hotel.room_orders (user_id, room_id, hotel.room_orders.date, hotel.room_orders.from, 
             hotel.room_orders.to, amount, status, prepayment) 
            VALUES (?,?,?,?,?,?,?,?)""";
    private static final String SQL_INSERT_DAILY_ROOM_STATE = """
            INSERT INTO hotel.daily_room_states (room_id, room_state, hotel.daily_room_states.date) 
            VALUES (?,?,?)""";
    private static final String SQL_UPDATE_USER_BALANCE = """
            UPDATE hotel.users SET balance=balance-? WHERE user_id=?";
            """;

    @Override
    public List<RoomOrder> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(RoomOrder roomOrder) throws DaoException {
        return false;
    }

    @Override
    public boolean create(RoomOrder roomOrder) throws DaoException {
        return false;
    }

    @Override
    public boolean createOrderWithRoomStates(RoomOrder roomOrder, int days) throws DaoException {
        boolean result = false;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(SQL_INSERT_ROOM_ORDER);
            statement.setLong(1, roomOrder.getUserId());
            statement.setLong(2, roomOrder.getRoomId());
            statement.setDate(3, Date.valueOf(roomOrder.getDate()));
            statement.setDate(4, Date.valueOf(roomOrder.getFrom()));
            statement.setDate(5, Date.valueOf(roomOrder.getTo()));
            statement.setBigDecimal(6, roomOrder.getAmount());
            statement.setString(7, roomOrder.getStatus().toString());
            statement.setBoolean(8, roomOrder.isPrepayment());
            statement.executeUpdate();

            LocalDate dateForStateChange = roomOrder.getFrom();
            statement = connection.prepareStatement(SQL_INSERT_DAILY_ROOM_STATE);
            for (int i = 0; i < days; i++) {
                statement.setLong(1, roomOrder.getRoomId());
                statement.setString(2, STATE_BOOKED);
                statement.setDate(3, Date.valueOf(dateForStateChange));
                statement.executeUpdate();
                dateForStateChange = dateForStateChange.plusDays(1);
            }

            if (roomOrder.isPrepayment()) {
                statement = connection.prepareStatement(SQL_UPDATE_USER_BALANCE);
                statement.setBigDecimal(1, roomOrder.getAmount());
                statement.setLong(2, roomOrder.getUserId());
                statement.executeUpdate();
            }
            result = true;
            connection.commit();

        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.room_orders was failed " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback create from table hotel.room_orders was failed " + e);
                throw new DaoException("Rollback create from table hotel.room_orders was failed", e);
            }
        }finally{
            try {
                connection.setAutoCommit(true);
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection or statment close was failed" + e);
                throw new DaoException("Connection or statment close was failed", e);
            }
        }
        return result;
    }

    @Override
    public Optional<RoomOrder> update(RoomOrder roomOrder) throws DaoException {
        return Optional.empty();
    }
}
