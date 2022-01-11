package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomDao extends BaseDao<Long, Room> {

    Map<Room, Image> findAllVisibleRoomsWithPreview() throws DaoException;

    List<Room> findAllVisibleRooms() throws DaoException;

    Optional<Room> findRoomById(long roomId) throws DaoException;
}
