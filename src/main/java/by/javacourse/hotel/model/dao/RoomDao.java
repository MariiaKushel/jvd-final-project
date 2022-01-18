package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomDao extends BaseDao<Long, Room> {

    List<Room> findAllVisibleRooms() throws DaoException;

    Optional<Room> findRoomById(long roomId) throws DaoException;

    BigDecimal minPrice() throws DaoException;

    BigDecimal maxPrice() throws DaoException;

    List<Integer> findAllPossibleSleepingPlace() throws DaoException;

    List<Room> findRoomByParameters(LocalDate dateFrom, LocalDate dateTo, BigDecimal priceFrom, BigDecimal priceTo,
                                    int[] sleepingPlaces) throws DaoException;


}
