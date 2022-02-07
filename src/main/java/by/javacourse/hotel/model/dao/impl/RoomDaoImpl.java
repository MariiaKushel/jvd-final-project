package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.RoomMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import by.javacourse.hotel.util.IntArrayConvector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class RoomDaoImpl implements RoomDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_ROOM = """
            INSERT INTO hotel.rooms (number, sleeping_place, price_per_day, rating, visible) 
            VALUES (?,?,?,?,?)""";
    private static final String SQL_UPDATE_ROOM = """
            UPDATE hotel.rooms SET number=?, sleeping_place=?, price_per_day=?, visible=?
            WHERE room_id=?""";
    private static final String SQL_UPDATE_RATING = """
            UPDATE hotel.rooms SET rating=(
                SELECT AVG(room_mark) AS new_rating
                FROM (
                    SELECT hotel.room_orders.room_id, room_mark, hotel.reviews.date
                    FROM hotel.reviews
                    JOIN hotel.room_orders ON review_id=room_order_id AND hotel.room_orders.room_id=? AND room_mark>0
                    ORDER BY hotel.reviews.date DESC LIMIT 20
                ) AS marks_by_room
            )
            WHERE hotel.rooms.room_id=?""";
    private static final String SQL_SELECT_ALL_ROOM = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            ORDER BY number""";
    private static final String SQL_SELECT_ROOM_BY_ID = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            WHERE hotel.rooms.room_id=? LIMIT 1""";
    private static final String SQL_SELECT_ROOM_BY_VISIBLE = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            WHERE visible=?
            ORDER BY rating DESC""";
    private static final String SQL_SELECT_ROOM_BY_NUMBER = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            WHERE number=?""";
    private static final String SQL_SELECT_ROOM_BY_SLEEPING_PLACE = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            WHERE sleeping_place=?""";
    private static final String SQL_SELECT_ROOM_BY_PRICE_RANGE = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            WHERE price_per_day BETWEEN ? AND ? ORDER BY price_per_day""";
    private static final String SQL_SELECT_ROOM_BY_PARAMETERS = """
            SELECT hotel.rooms.room_id, number, sleeping_place, price_per_day, rating, visible, image 
            FROM hotel.rooms
            LEFT JOIN hotel.images ON hotel.rooms.room_id=hotel.images.room_id AND preview=true
            LEFT JOIN hotel.daily_room_states ON hotel.rooms.room_id=hotel.daily_room_states.room_id
                AND hotel.daily_room_states.date BETWEEN ? AND ?
            WHERE visible=true
            AND daily_room_state_id IS NULL
            AND price_per_day BETWEEN ? AND ?""";
    private static final String AND_SLEEPING_PLACE = """
            AND sleeping_place IN  """;
    private static final String ORDER_BY_PRICE = """
            ORDER BY price_per_day""";
    private static final String SQL_SELECT_MIN_PRICE = "SELECT min(price_per_day) AS min_price FROM hotel.rooms";
    private static final String SQL_SELECT_MAX_PRICE = "SELECT max(price_per_day) AS max_price FROM hotel.rooms";
    private static final String SQL_SELECT_All_POSSIBLE_SLEEPING_PLACE = """
            SELECT DISTINCT sleeping_place FROM hotel.rooms
            WHERE visible=true
            ORDER BY sleeping_place""";

    @Override
    public List<Room> findAll() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ROOM)) {
            rooms = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request findAll from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public boolean delete(Room room) throws DaoException {
        logger.error("Unavailable operation to entity <Room>");
        throw new UnsupportedOperationException("Unavailable operation to entity <Room>");
    }

    @Override
    public boolean create(Room room) throws DaoException {
        int rows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ROOM)) {
            statement.setInt(1, room.getNumber());
            statement.setInt(2, room.getSleepingPlace());
            statement.setBigDecimal(3, room.getPricePerDay());
            statement.setBigDecimal(4, room.getRating());
            statement.setBoolean(5, room.isVisible());
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request create from table hotel.rooms was failed", e);
        }
        return rows == 1;
    }

    @Override
    public List<Room> findRoomByVisible(boolean visible) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_VISIBLE)) {
            statement.setBoolean(1, visible);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findRoomByVisible from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request findRoomByVisible from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    public Optional<Room> findEntityById(Long roomId) throws DaoException {
        Optional<Room> room = Optional.empty();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_ID)) {
            statement.setLong(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                room = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findEntityById from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findEntityById from table hotel.rooms was failed", e);
        }
        return room;
    }

    @Override
    public List<Room> findRoomByNumber(int number) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_NUMBER)) {
            statement.setInt(1, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findRoomByNumber from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findRoomByNumber from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomBySleepingPlace(int sleepingPlace) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_SLEEPING_PLACE)) {
            statement.setInt(1, sleepingPlace);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findRoomBySleepingPlace from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findRoomBySleepingPlace from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomByPriceRange(BigDecimal from, BigDecimal to) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_PRICE_RANGE)) {
            statement.setBigDecimal(1, from);
            statement.setBigDecimal(2, to);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findRoomByPriceRange from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findRoomByPriceRange from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public BigDecimal minPrice() throws DaoException {
        BigDecimal minPrice = BigDecimal.ZERO;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_MIN_PRICE)) {
            if (resultSet.next()) {
                minPrice = resultSet.getBigDecimal(MIN_PRICE);
            }
        } catch (SQLException e) {
            logger.error("SQL request minPrice from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request minPrice from table hotel.rooms was failed", e);
        }
        return minPrice;
    }

    @Override
    public BigDecimal maxPrice() throws DaoException {
        BigDecimal maxPrice = BigDecimal.ZERO;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_MAX_PRICE)) {
            if (resultSet.next()) {
                maxPrice = resultSet.getBigDecimal(MAX_PRICE);
            }
        } catch (SQLException e) {
            logger.error("SQL request maxPrice from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request maxPrice from table hotel.rooms was failed", e);
        }
        return maxPrice;
    }

    @Override
    public List<Integer> findAllPossibleSleepingPlace() throws DaoException {
        List<Integer> allSleepingPlace = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_All_POSSIBLE_SLEEPING_PLACE)) {
            while (resultSet.next()) {
                int sleepingPlace = resultSet.getInt(SLEEPING_PLACE);
                allSleepingPlace.add(sleepingPlace);
            }
        } catch (SQLException e) {
            logger.error("SQL request findAllPossibleSleepingPlace from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findAllPossibleSleepingPlace from table hotel.rooms was failed", e);
        }
        return allSleepingPlace;
    }

    @Override
    public List<Room> findRoomByParameters(LocalDate dateFrom, LocalDate dateTo, BigDecimal priceFrom, BigDecimal priceTo,
                                           int[] sleepingPlaces) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        StringBuilder sql_request = new StringBuilder()
                .append(SQL_SELECT_ROOM_BY_PARAMETERS);
        if (sleepingPlaces.length > 0) {
            String dynamicSqlRequestPart = IntArrayConvector.convertToSqlRequestPart(sleepingPlaces);
            sql_request.append(AND_SLEEPING_PLACE)
                    .append(dynamicSqlRequestPart);
        }
        sql_request.append(ORDER_BY_PRICE);

        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_request.toString())) {
            statement.setDate(1, Date.valueOf(dateFrom));
            statement.setDate(2, Date.valueOf(dateTo));
            statement.setBigDecimal(3, priceFrom);
            statement.setBigDecimal(4, priceTo);
            if (sleepingPlaces.length > 0) {
                for (int pos = 5, i = 0; i < sleepingPlaces.length; pos++, i++)
                    statement.setInt(pos, sleepingPlaces[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findRoomByParameters from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findRoomByParameters from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public boolean refreshRating(long roomId) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_RATING)) {
            statement.setLong(1, roomId);
            statement.setLong(2, roomId);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request refreshRating from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request refreshRating from table hotel.rooms was failed", e);
        }
        return rowsUpdated == 1;
    }

    @Override
    public boolean update(Room room) throws DaoException {
        int rowsUpdated = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ROOM)) {
            statement.setInt(1, room.getNumber());
            statement.setInt(2, room.getSleepingPlace());
            statement.setBigDecimal(3, room.getPricePerDay());
            statement.setBoolean(4, room.isVisible());
            statement.setLong(5, room.getEntityId());
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request update1 from table hotel.rooms was failed", e);
        }
        return rowsUpdated == 1;
    }
}
