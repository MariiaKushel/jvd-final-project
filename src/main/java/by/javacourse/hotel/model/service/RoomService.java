package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomService {
    List<Room> findAllRooms() throws ServiceException;

    List<Room> findAllVisibleRooms() throws ServiceException;

    Optional<Room> findRoomById(long roomId) throws ServiceException;

    BigDecimal findMinPrice() throws ServiceException;

    BigDecimal findMaxPrice() throws ServiceException;

    List<Integer> findAllPossibleSleepingPlace() throws ServiceException;

    List<Room> findRoomByParameters(Map<String,String> parameters, String...sleepPlaces)throws ServiceException;
}
