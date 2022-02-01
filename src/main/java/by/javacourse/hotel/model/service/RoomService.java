package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code RoomService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.Room}
 */
public interface RoomService {

    /**
     * Find all rooms
     * @return room list or empty list if room not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findAllRooms() throws ServiceException;

    /**
     * Find all rooms which have visible mark
     * @return room list or empty list if room not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findAllVisibleRooms() throws ServiceException;

    /**
     * Find room by id
     * @param roomId - room id
     * @return an Optional describing room, or an empty Optional if room not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<Room> findRoomById(String roomId) throws ServiceException;

    /**
     * Find room by number
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return room list or empty list if room not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findRoomByNumber(Map<String, String> parameters) throws ServiceException;

    /**
     * Find room by sleeping place
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return room list or empty list if room not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findRoomBySleepingPlace(Map<String, String> parameters) throws ServiceException;

    /**
     * Find room by price range
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return room list or empty list if room not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findRoomByPriceRange(Map<String, String> parameters) throws ServiceException;

    /**
     * Find room by visible
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return room list or empty list if room not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findRoomByVisible(Map<String, String> parameters) throws ServiceException;

    /**
     * Find min room price
     * @return value of min price
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    BigDecimal findMinPrice() throws ServiceException;

    /**
     * Find max room price
     * @return value of max price
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    BigDecimal findMaxPrice() throws ServiceException;

    /**
     * Find all possible sleeping place by all visible room
     * @return list of all possible sleeping place
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Integer> findAllPossibleSleepingPlace() throws ServiceException;

    /**
     * Find room by parameters
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return room list or empty list if room not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Room> findRoomByParameters(Map<String, String> parameters, String... sleepPlaces) throws ServiceException;

    /**
     * Create room
     * @param roomData - map with room data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if room was created and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createRoom(Map<String, String> roomData) throws ServiceException;

    /**
     * Update room
     * @param roomData - map with room data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if room was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updateRoom(Map<String, String> roomData) throws ServiceException;
}
