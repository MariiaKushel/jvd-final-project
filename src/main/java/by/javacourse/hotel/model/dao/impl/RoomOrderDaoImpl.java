package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.RoomOrderDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.RoomOrderMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomOrderDaoImpl implements RoomOrderDao {
    static Logger logger = LogManager.getLogger();

    private static final String STATE_BOOKED = "booked"; // FIXME maybe enum
    private static final String STATE_BUSY = "busy";

    private static final String SQL_INSERT_ROOM_ORDER = """
            INSERT INTO hotel.room_orders (user_id, room_id, hotel.room_orders.date, hotel.room_orders.from, 
             hotel.room_orders.to, amount, status, prepayment) 
            VALUES (?,?,?,?,?,?,?,?)""";
    private static final String SQL_INSERT_DAILY_ROOM_STATE = """
            INSERT INTO hotel.daily_room_states (room_id, room_state, hotel.daily_room_states.date) 
            VALUES (?,?,?)""";
    private static final String SQL_UPDATE_USER_BALANCE = """
            UPDATE hotel.users SET balance=balance-? WHERE user_id=?;
            """;

    private static final String SQL_UPDATE_ROOM_ORDER = """
            UPDATE hotel.room_orders SET status=? 
            WHERE room_order_id=?""";

    private static final String SQL_DELETE_DAILY_ROOM_STATE = """
            DELETE FROM hotel.daily_room_states 
            WHERE room_id=? AND hotel.daily_room_states.date BETWEEN ? AND ?""";

    private static final String SQL_UPDATE_DAILY_ROOM_STATE = """
            UPDATE hotel.daily_room_states 
            SET room_state=?
            WHERE room_id=? AND hotel.daily_room_states.date BETWEEN ? AND ?""";

    private static final String SQL_SELECT_ROOM_ORDER = """
            SELECT room_order_id, user_id, room_id, date, hotel.room_orders.from, hotel.room_orders.to, amount, status, 
             prepayment
            FROM hotel.room_orders""";

    private static final String BY_PREPAYMENT = " WHERE prepayment=?";
    private static final String BY_STATUS = " WHERE status=?";
    private static final String BY_DATE_RANGE = "  WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    private static final String BY_USER_ID = " WHERE user_id=? ORDER BY date DESC";
    private static final String BY_USER_ID_LAST = " WHERE user_id=? ORDER BY date DESC LIMIT ?";
    private static final String BY_ID = " WHERE room_order_id=? LIMIT 1";

    private static final String SQL_SELECT_CHECK_ROOM_STATE = """
            SELECT room_id, room_state
            FROM hotel.daily_room_states
            WHERE room_id=? AND date BETWEEN ? and ?""";

    @Override
    public List<RoomOrder> findAll() throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ROOM_ORDER)) {
            orders = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findAll from table hotel.room_orders was failed", e);
        }
        return orders;
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
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(SQL_SELECT_CHECK_ROOM_STATE);
            statement.setLong(1, roomOrder.getRoomId());
            statement.setDate(2, Date.valueOf(roomOrder.getFrom()));
            statement.setDate(3, Date.valueOf(roomOrder.getTo()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return result;
            }

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
        } finally {
            try {
                connection.setAutoCommit(true);
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection or statement close was failed" + e);
                throw new DaoException("Connection or statement close was failed", e);
            }
        }
        return result;
    }

    @Override
    public List<RoomOrder> findOrderByPrepayment(boolean prepayment) throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_PREPAYMENT)) {
            statement.setBoolean(1, prepayment);
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderByPrepayment from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderByPrepayment from table hotel.room_orders was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByStatus(RoomOrder.Status status) throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_STATUS)) {
            statement.setString(1, status.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderByStatus from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderByStatus from table hotel.room_orders was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByDateRange(LocalDate from, LocalDate to) throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_DATE_RANGE)) {
            statement.setDate(1, Date.valueOf(from));
            statement.setDate(2, Date.valueOf(to));
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderByDateRange from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderByDateRange from table hotel.room_orders was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByUserId(long userId) throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderByUserId from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderByUserId from table hotel.room_orders was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByUserIdLast(long userId, int last) throws DaoException {
        List<RoomOrder> orders = new ArrayList<>();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_USER_ID_LAST)) {
            statement.setLong(1, userId);
            statement.setInt(2, last);
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderByUserIdLast from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderByUserIdLast from table hotel.room_orders was failed", e);
        }
        return orders;
    }

    @Override
    public Optional<RoomOrder> findOrderById(long orderId) throws DaoException {
        Optional<RoomOrder> order = Optional.empty();
        Mapper mapper = RoomOrderMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_ORDER + BY_ID)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                order = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findOrderById from table hotel.room_orders was failed" + e);
            throw new DaoException("SQL request findOrderById from table hotel.room_orders was failed", e);
        }
        return order;
    }

    @Override
    public Optional<RoomOrder> update(RoomOrder roomOrder) throws DaoException {

        return Optional.empty();
    }

    @Override
    public boolean update1(RoomOrder roomOrder) throws DaoException {
        boolean result = false;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            System.out.println("roomOrder.getStatus().toString()" + roomOrder.getStatus().toString());

            statement = connection.prepareStatement(SQL_UPDATE_ROOM_ORDER);
            statement.setString(1, roomOrder.getStatus().toString());
            statement.setLong(2, roomOrder.getEntityId());
            statement.executeUpdate();

            RoomOrder.Status currentStatus = roomOrder.getStatus();
            switch (currentStatus) {
                case CANCELED_BY_CLIENT, CANCELED_BY_ADMIN -> {
                    statement = connection.prepareStatement(SQL_DELETE_DAILY_ROOM_STATE);
                    statement.setLong(1, roomOrder.getRoomId());
                    statement.setDate(2, Date.valueOf(roomOrder.getFrom()));
                    statement.setDate(3, Date.valueOf(roomOrder.getTo()));
                    statement.executeUpdate();

                    if (roomOrder.isPrepayment()) {
                        statement = connection.prepareStatement(SQL_UPDATE_USER_BALANCE);
                        statement.setBigDecimal(1, roomOrder.getAmount().multiply(new BigDecimal(-1)));
                        statement.setLong(2, roomOrder.getUserId());
                        statement.executeUpdate();
                    }
                }
                case CONFIRMED -> {
                    //nothing todo
                }
                case IN_PROGRESS -> {
                    statement = connection.prepareStatement(SQL_UPDATE_DAILY_ROOM_STATE);
                    statement.setString(1, STATE_BUSY);
                    statement.setLong(2, roomOrder.getRoomId());
                    statement.setDate(3, Date.valueOf(roomOrder.getFrom()));
                    statement.setDate(4, Date.valueOf(roomOrder.getTo()));
                    statement.executeUpdate();
                }
                case COMPLETED -> {
                    //nothing todo
                }
            }
            result = true;
            connection.commit();

        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.room_orders was failed " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback update1 from table hotel.room_orders was failed " + e);
                throw new DaoException("Rollback update1 from table hotel.room_orders was failed", e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection or statement close was failed" + e);
                throw new DaoException("Connection or statement close was failed", e);
            }
        }
        return result;
    }

}
