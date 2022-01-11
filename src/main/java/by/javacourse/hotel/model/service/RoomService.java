package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> findAllRooms() throws ServiceException;

    List<Room> findAllVisibleRooms() throws ServiceException;

    Optional<Room> findRoomById(long roomId) throws ServiceException;
}
