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

    private static final String SQL_INSERT_ROOM_ORDER = """
            INSERT INTO hotel.room_orders (user_id, room_id, date, from, to, amount, status, prepayment) 
            VALUES (?,?,?,?,?,?,?,?)""";
    private static final String SQL_INSERT_DAILY_ROOM_STATE = """
            INSERT INTO hotel.daily_room_states (room_id, room_state, date) 
            VALUES (?,?,?)""";
    private static final String SQL_UPDATE_USER_BALANCE = """
            UPDATE hotel.users SET balance=balance-? WHERE user_id=?";
            """;
    private static final String SQL_SELECT_USER_BALANCE_BY_USER_ID = """
            SELECT balance FROM hotel.users
            WHERE user_id=? LIMIT 1""";


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
        boolean result = false;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ROOM_ORDER);
            statement.setLong(1, roomOrder.getUserId());
            statement.setLong(2, roomOrder.getRoomId());
            statement.setDate(3, Date.valueOf(roomOrder.getDate()));
            statement.setDate(4, Date.valueOf(roomOrder.getFrom()));
            statement.setDate(4, Date.valueOf(roomOrder.getTo()));
            statement.setBigDecimal(5, roomOrder.getAmount());
            statement.setString(6, roomOrder.getStatus().toString());
            statement.setBoolean(7, roomOrder.isPrepayment());
            int insertedRoom = statement.executeUpdate();
            if (insertedRoom != 1) {
                connection.rollback();
            }


            Period period = Period.between(roomOrder.getFrom(), roomOrder.getTo());
            int days = period.getDays();
            LocalDate currentDate = roomOrder.getFrom();
            int insertedStates = 0;
            for (int i = 0; i < days; i++) {
                statement = connection.prepareStatement(SQL_INSERT_DAILY_ROOM_STATE);
                statement.setLong(1, roomOrder.getRoomId());
                statement.setString(2, "booked");
                statement.setDate(3, Date.valueOf(currentDate));
                insertedStates += statement.executeUpdate();
                currentDate = currentDate.plusDays(1);
            }
            if (insertedStates != days) {
                connection.rollback();
            }

            if (roomOrder.isPrepayment()) {
                int updatedBalance = 0;
                statement = connection.prepareStatement(SQL_UPDATE_USER_BALANCE);
                statement.setBigDecimal(1, roomOrder.getAmount());
                statement.setLong(2, roomOrder.getUserId());
                updatedBalance = statement.executeUpdate();
                statement = connection.prepareStatement(SQL_SELECT_USER_BALANCE_BY_USER_ID);
                statement.setLong(1, roomOrder.getUserId());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    BigDecimal newBalance = resultSet.getBigDecimal(ColumnName.BALANCE);
                    if (newBalance.compareTo(new BigDecimal(0)) < 0) {
                        updatedBalance = 0;
                    }
                }
                if (updatedBalance != 1) {
                    connection.rollback();
                }
            }
            result = true;
            connection.commit();

        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.room_orders was failed " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("SQL request rollback create from table hotel.room_orders was failed " + e);
                throw new DaoException("SQL request rollback create from table hotel.room_orders was failed", e);
            }
        }finally{
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("SQL request close create from table hotel.room_orders was failed " + e);
                throw new DaoException("SQL request close create from table hotel.room_orders was failed", e);
            }
        }
        return result;
    }

    @Override
    public Optional<RoomOrder> update(RoomOrder roomOrder) throws DaoException {
        return Optional.empty();
    }
}
