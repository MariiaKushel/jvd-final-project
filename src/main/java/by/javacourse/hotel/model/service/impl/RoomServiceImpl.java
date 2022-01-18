package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.validator.RoomSearchParameterValidator;
import by.javacourse.hotel.validator.impl.RoomSearchParameterValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static by.javacourse.hotel.controller.command.RequestParameter.*;

public class RoomServiceImpl implements RoomService {
    static Logger logger = LogManager.getLogger();
    private static final BigDecimal DEFAULT_MIN_PRICE = new BigDecimal(0);
    private static final BigDecimal DEFAULT_MAX_PRICE = new BigDecimal(9999999.99);
    private static final int[] DEFAULT_SLEEPING_PLACES = new int[0];
    private static final String WRONG_DATA_MARKER = "";

    private DaoProvider provider = DaoProvider.getInstance();
    private RoomDao roomDao = provider.getRoomDao();

    @Override
    public List<Room> findAllRooms() throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = roomDao.findAll();
        } catch (DaoException e) {
            logger.error("Try to find all rooms was failed " + e);
            throw new ServiceException("Try to find all rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findAllVisibleRooms() throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = roomDao.findAllVisibleRooms();
        } catch (DaoException e) {
            logger.error("Try to find all visible rooms was failed " + e);
            throw new ServiceException("Try to find all visible rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public Optional<Room> findRoomById(long roomId) throws ServiceException {
        Optional<Room> room = Optional.empty();
        try {
            room = roomDao.findRoomById(roomId);
        } catch (DaoException e) {
            logger.error("Try to find rooms by id was failed " + e);
            throw new ServiceException("Try to find rooms by id  was failed", e);
        }
        return room;
    }

    @Override
    public BigDecimal findMinPrice() throws ServiceException {
        BigDecimal minPrice = DEFAULT_MIN_PRICE;
        try {
            minPrice = roomDao.minPrice();
        } catch (DaoException e) {
            logger.error("Try to findMinPrice was failed " + e);
            throw new ServiceException("Try to findMinPrice  was failed", e);
        }
        return minPrice;
    }

    @Override
    public BigDecimal findMaxPrice() throws ServiceException {
        BigDecimal maxPrice = DEFAULT_MAX_PRICE;
        try {
            maxPrice = roomDao.maxPrice();
        } catch (DaoException e) {
            logger.error("Try to findMaxPrice was failed " + e);
            throw new ServiceException("Try to findMaxPrice  was failed", e);
        }
        return maxPrice;
    }

    @Override
    public List<Integer> findAllPossibleSleepingPlace() throws ServiceException {
        List<Integer> allSleepingPalace = new ArrayList<>();
        try {
            allSleepingPalace = roomDao.findAllPossibleSleepingPlace();
        } catch (DaoException e) {
            logger.error("Try to findAllPossibleSleepingPlace was failed " + e);
            throw new ServiceException("Try to findAllPossibleSleepingPlace  was failed", e);
        }
        return allSleepingPalace;
    }

    @Override
    public List<Room> findRoomByParameters(Map<String,String> parameters, String...sleepPlaces) throws ServiceException {
        LocalDate dateFrom = LocalDate.parse(parameters.get(DATE_FROM));
        LocalDate dateTo = LocalDate.parse(parameters.get(DATE_TO));


        String priceFromTemp = parameters.get(PRICE_FROM);
        BigDecimal priceFrom = priceFromTemp.isEmpty()
                ? DEFAULT_MIN_PRICE
                : new BigDecimal(priceFromTemp);

        String priceToTemp = parameters.get(PRICE_TO);
        BigDecimal priceTo = priceToTemp.isEmpty()
                ? DEFAULT_MAX_PRICE
                : new BigDecimal(priceToTemp);

        String[] sleepingPlacesTemp = sleepPlaces;
        int[] sleepingPlaces = sleepingPlacesTemp == null
                ? DEFAULT_SLEEPING_PLACES
                : Stream.of(sleepingPlacesTemp).mapToInt(s -> Integer.parseInt(s)).toArray();

        List<Room> rooms = new ArrayList<>();
        RoomSearchParameterValidator validator = RoomSearchParameterValidatorImpl.getInstance();

        if (!validator.validateDate(dateFrom, dateTo)||!validator.validatePrice(priceFrom, priceTo)) {
            parameters.put(DATE_FROM, WRONG_DATA_MARKER);
            parameters.put(DATE_TO, WRONG_DATA_MARKER);
            parameters.put(PRICE_FROM, WRONG_DATA_MARKER);
            parameters.put(PRICE_TO, WRONG_DATA_MARKER);
            return rooms;
        }

        try {
            rooms = roomDao.findRoomByParameters(dateFrom, dateTo, priceFrom, priceTo, sleepingPlaces);
        } catch (DaoException e) {
            logger.error("Try to findRoomByParameters was failed " + e);
            throw new ServiceException("Try to findRoomByParameters  was failed", e);
        }
        return rooms;
    }
}
