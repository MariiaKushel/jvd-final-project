package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * {@code RoomDao} class implements functional of {@link BaseDao}
 */
public interface RoomDao extends BaseDao<Long, Room> {

    /**
     * Find room by visible
     * @param visible - room visible
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Room> findRoomByVisible(boolean visible) throws DaoException;

    /**
     * Find room by number
     * @param number - room number
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Room> findRoomByNumber(int number) throws DaoException;

    /**
     * Find room by sleepingPlace
     * @param sleepingPlace - room sleepingPlace
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Room> findRoomBySleepingPlace(int sleepingPlace) throws DaoException;

    /**
     * Find room by price range
     * @param from - low border of range
     * @param to - upper border of range
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Room> findRoomByPriceRange(BigDecimal from, BigDecimal to) throws DaoException;

    /**
     * Find min price by all room
     * @return value of min price
     * @throws DaoException - if request from database was failed
     */
    BigDecimal minPrice() throws DaoException;

    /**
     * Find max price by all room
     * @return value of max price
     * @throws DaoException - if request from database was failed
     */
    BigDecimal maxPrice() throws DaoException;

    /**
     * Find all possible sleeping places by all visible room
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Integer> findAllPossibleSleepingPlace() throws DaoException;

    /**
     * Find room by price range
     * @param dateFrom - low border of date range
     * @param dateTo - upper border of date range
     * @param priceFrom - low border of price range
     * @param priceTo - upper border of price range
     * @param sleepingPlaces - number of sleeping places or empty array, is need search for all sleeping places
     * @return list of room or empty list if room not found
     * @throws DaoException - if request from database was failed
     */
    List<Room> findRoomByParameters(LocalDate dateFrom, LocalDate dateTo, BigDecimal priceFrom, BigDecimal priceTo,
                                    int[] sleepingPlaces) throws DaoException;

    /**
     * Update room rating
     * @param roomId - room id
     * @return true - if room rating was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean refreshRating(long roomId) throws DaoException;

    /**
     * Find number of all visible rooms
     * @return number of all visible rooms
     * @throws DaoException - if request from database was failed
     */
    int findNumberOfVisibleRoom() throws DaoException;

    /**
     * Find room all visible rooms which have id more than beforeRoomId.
     * @param beforeRoomId - low limit as room id
     * @return list of room or empty list if room not found. List size <= default page capacity
     * @throws DaoException - if request from database was failed
     */
    List<Room> findPrevious(long beforeRoomId) throws DaoException;

    /**
     * Find room all visible rooms which have id less than afterRoomId.
     * @param afterRoomId - low limit as room id
     * @return list of room or empty list if room not found. List size <= default page capacity
     * @throws DaoException - if request from database was failed
     */
    List<Room> findNext(long afterRoomId) throws DaoException;

}
