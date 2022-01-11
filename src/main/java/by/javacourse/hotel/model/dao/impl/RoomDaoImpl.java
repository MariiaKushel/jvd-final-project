package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.RoomMapper;
import by.javacourse.hotel.model.dao.mapper.impl.UserMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class RoomDaoImpl implements RoomDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_ROOM = """
            INSERT INTO hotel.rooms (number, sleeping_place, price_per_day, rating, visible, description) 
            VALUES (?,?,?,?,?,?)""";

    private static final String SQL_SELECT_ROOM = """
            SELECT room_id, number, sleeping_place, price_per_day, rating, visible, description 
            FROM hotel.rooms""";

    private static final String BY_ID = " WHERE room_id=? LIMIT 1";
    private static final String BY_VISIBLE = " WHERE visible=?";

    @Override
    public List<Room> findAll() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ROOM)) {
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
        int rowsInserted = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ROOM)) {
            statement.setInt(1, room.getNumber());
            statement.setInt(2, room.getSleepingPlace());
            statement.setBigDecimal(3, room.getPricePerDay());
            statement.setBigDecimal(4, room.getRating());
            statement.setBoolean(5, room.isVisible());
            statement.setString(6, room.getDescription());
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request create from table hotel.rooms was failed", e);
        }
        return rowsInserted == 1;
    }

    @Override
    public Optional<Room> update(Room room) throws DaoException {
        Optional<Room> oldRoom = Optional.empty();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM + BY_ID,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) { // FIXME need TYPE_SCROLL_INSENSITIVE?
            statement.setLong(1, room.getEntityId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //get old room
                    oldRoom = mapper.retrieve(resultSet).stream().findFirst();
                    //update room
                    resultSet.first();
                    resultSet.updateInt(NUMBER, room.getNumber());
                    resultSet.updateInt(SLEEPING_PLACE, room.getSleepingPlace());
                    resultSet.updateBigDecimal(PRICE_PER_DAY, room.getPricePerDay());
                    resultSet.updateBigDecimal(RATING, room.getRating());
                    resultSet.updateBoolean(VISIBLE, room.isVisible());
                    resultSet.updateString(DESCRIPTION, room.getDescription());
                    resultSet.updateRow();
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request update from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request update from table hotel.rooms was failed", e);
        }
        return oldRoom;
    }

    @Override
    public Map<Room, Image> findAllVisibleRoomsWithPreview() throws DaoException {//////////////////////////////////
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM + BY_VISIBLE)) {
            statement.setBoolean(1, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findAllVisibleRooms from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request findAllVisibleRooms from table hotel.rooms was failed", e);
        }
        return null;
    }

    @Override
    public List<Room> findAllVisibleRooms() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM + BY_VISIBLE)) {
            statement.setBoolean(1, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                rooms = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findAllVisibleRooms from table hotel.rooms was failed" + e);
            throw new DaoException("SQL request findAllVisibleRooms from table hotel.rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public Optional<Room> findRoomById(long roomId) throws DaoException {
        Optional<Room> room = Optional.empty();
        Mapper mapper = RoomMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM + BY_ID)) {
            statement.setLong(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                room = mapper.retrieve(resultSet).stream().findFirst();
            }
            logger.debug("room> " + room);
        } catch (SQLException e) {
            logger.error("SQL request findRoomById from table hotel.rooms was failed " + e);
            throw new DaoException("SQL request findRoomById from table hotel.rooms was failed", e);
        }
        return room;
    }
}
