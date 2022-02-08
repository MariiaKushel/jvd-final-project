package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.validator.RoomValidator;
import by.javacourse.hotel.validator.impl.RoomValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class RoomServiceImpl implements RoomService {
    static Logger logger = LogManager.getLogger();

    private static final String DEFAULT_MIN_PRICE = "0";
    private static final String DEFAULT_MAX_PRICE = "9999999.99";
    private static final int[] DEFAULT_SLEEPING_PLACES = new int[0];
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
    public List<Room> findAllVisibleRooms(String direction, Map<String, Long> paginationData) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        try {
            if (paginationData.isEmpty()
                    || direction == null
                    || (paginationData.get(CURRENT_SHEET_SES) == 1 && RoomValidator.PREVIOUS_SHEET.equals(direction)
                    || (paginationData.get(CURRENT_SHEET_SES) == paginationData.get(ALL_SHEET_SES)
                    && RoomValidator.NEXT_SHEET.equals(direction)))) {
                direction = RoomValidator.NEXT_SHEET;
                preparePaginationData(paginationData);
            }
            RoomValidator validator = RoomValidatorImpl.getInstance();
            if (!validator.validateDirection(direction)) {
                paginationData.clear();
                logger.info("Not valid direction");
                return rooms;
            }

            if (direction.equals(RoomValidator.NEXT_SHEET)) {
                long lastId = paginationData.get(LAST_ID_SES);
                rooms = roomDao.findNext(lastId);
            } else {
                long firstId = paginationData.get(FIRST_ID_SES);
                rooms = roomDao.findPrevious(firstId);
            }
            refreshPaginationData(direction, paginationData, rooms);
        } catch (DaoException e) {
            logger.error("Try to find all visible rooms was failed " + e);
            throw new ServiceException("Try to find all visible rooms was failed", e);
        }
        return rooms;
    }

    @Override
    public Optional<Room> findRoomById(String roomId) throws ServiceException {
        Optional<Room> room = Optional.empty();
        try {
            long roomIdL = Long.parseLong(roomId);
            room = roomDao.findEntityById(roomIdL);
        } catch (NumberFormatException e) {
            logger.info("Not valid room id");
        } catch (DaoException e) {
            logger.error("Try to find rooms by id was failed " + e);
            throw new ServiceException("Try to find rooms by id  was failed", e);
        }
        return room;
    }

    @Override
    public List<Room> findRoomByNumber(Map<String, String> parameters) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        String tempNumber = parameters.get(ROOM_NUMBER_ATR);
        RoomValidator validator = RoomValidatorImpl.getInstance();
        if (!validator.validateNumber(tempNumber)) {
            parameters.put(WRONG_NUMBER_ATR, RoomValidator.WRONG_DATA_MARKER);
            return rooms;
        }
        try {
            if (!tempNumber.isEmpty()) {
                int number = Integer.parseInt(tempNumber);
                rooms = roomDao.findRoomByNumber(number);
            } else {
                rooms = roomDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find rooms by number was failed " + e);
            throw new ServiceException("Try to find rooms by number  was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomBySleepingPlace(Map<String, String> parameters) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        String tempSleepingPlace = parameters.get(SLEEPING_PLACES_ATR);
        try {
            if (!tempSleepingPlace.isEmpty()) {
                int sleepingPlace = Integer.parseInt(parameters.get(SLEEPING_PLACES_ATR));
                rooms = roomDao.findRoomBySleepingPlace(sleepingPlace);
            } else {
                rooms = roomDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find rooms by sleeping places was failed " + e);
            throw new ServiceException("Try to find rooms by sleeping places  was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomByPriceRange(Map<String, String> parameters) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        String tempFrom = parameters.get(PRICE_FROM_ATR);
        tempFrom = tempFrom.isEmpty()
                ? DEFAULT_MIN_PRICE
                : tempFrom;
        String tempTo = parameters.get(PRICE_TO_ATR);
        tempTo = tempTo.isEmpty()
                ? DEFAULT_MAX_PRICE
                : tempTo;
        RoomValidator validator = RoomValidatorImpl.getInstance();
        if (!validator.validatePriceRange(tempFrom, tempTo)) {
            parameters.put(WRONG_PRICE_RANGE_ATR, RoomValidator.WRONG_DATA_MARKER);
            return rooms;
        }
        try {
            BigDecimal priceFrom = new BigDecimal(tempFrom);
            BigDecimal priceTo = new BigDecimal(tempTo);
            rooms = roomDao.findRoomByPriceRange(priceFrom, priceTo);
        } catch (DaoException e) {
            logger.error("Try to find rooms by price range was failed " + e);
            throw new ServiceException("Try to find rooms by price range  was failed", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomByVisible(Map<String, String> parameters) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        String tempVisible = parameters.get(VISIBLE_ATR);
        try {
            if (!tempVisible.isEmpty()) {
                boolean visible = Boolean.parseBoolean(tempVisible);
                rooms = roomDao.findRoomByVisible(visible);
            } else {
                rooms = roomDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find rooms by sleeping places was failed " + e);
            throw new ServiceException("Try to find rooms by sleeping places  was failed", e);
        }
        return rooms;
    }

    @Override
    public BigDecimal findMinPrice() throws ServiceException {
        BigDecimal minPrice = BigDecimal.ZERO;
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
        BigDecimal maxPrice = BigDecimal.ZERO;
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
    public List<Room> findRoomByParameters(Map<String, String> parameters, String... sleepPlaces) throws ServiceException {
        List<Room> rooms = new ArrayList<>();
        RoomValidator validator = RoomValidatorImpl.getInstance();
        String tempDateFrom = parameters.get(DATE_FROM_ATR);
        String tempDateTo = parameters.get(DATE_TO_ATR);
        String tempPriceFrom = parameters.get(PRICE_FROM_ATR);
        tempPriceFrom = tempPriceFrom.isEmpty()
                ? DEFAULT_MIN_PRICE
                : tempPriceFrom;
        String TempPriceTo = parameters.get(PRICE_TO_ATR);
        TempPriceTo = TempPriceTo.isEmpty()
                ? DEFAULT_MAX_PRICE
                : TempPriceTo;
        if (!validator.validateDateRange(tempDateFrom, tempDateTo)
                || !validator.validatePriceRange(tempPriceFrom, TempPriceTo)) {
            logger.info("Some search parameters are not valid");
            parameters.put(WRONG_DATE_OR_PRICE_RANGE_ATR, RoomValidator.WRONG_DATA_MARKER);
            return rooms;
        }
        LocalDate dateFrom = LocalDate.parse(tempDateFrom);
        LocalDate dateTo = LocalDate.parse(tempDateTo);
        BigDecimal priceFrom = new BigDecimal(tempPriceFrom);
        BigDecimal priceTo = new BigDecimal(TempPriceTo);
        String[] sleepingPlacesTemp = sleepPlaces;
        int[] sleepingPlaces = sleepingPlacesTemp == null
                ? DEFAULT_SLEEPING_PLACES
                : Stream.of(sleepingPlacesTemp).mapToInt(s -> Integer.parseInt(s)).toArray();
        try {
            rooms = roomDao.findRoomByParameters(dateFrom, dateTo, priceFrom, priceTo, sleepingPlaces);
        } catch (DaoException e) {
            logger.error("Try to findRoomByParameters was failed " + e);
            throw new ServiceException("Try to findRoomByParameters  was failed", e);
        }
        return rooms;
    }

    @Override
    public boolean createRoom(Map<String, String> roomData) throws ServiceException {
        boolean isCreated = false;
        RoomValidator validator = RoomValidatorImpl.getInstance();
        if (!validator.validateRoomData(roomData)) {
            logger.info("Not valid data");
            return isCreated;
        }
        int number = Integer.parseInt(roomData.get(ROOM_NUMBER_SES));
        int sleepingPlace = Integer.parseInt(roomData.get(SLEEPING_PLACE_SES));
        BigDecimal price = new BigDecimal(roomData.get(PRICE_SES));
        boolean visible = roomData.get(VISIBLE_SES) != null
                ? Boolean.parseBoolean(roomData.get(VISIBLE_SES))
                : Boolean.FALSE;
        Room room = Room.newBuilder()
                .setNumber(number)
                .setSleepingPlace(sleepingPlace)
                .setPricePerDay(price)
                .setVisible(visible)
                .setRating(BigDecimal.ZERO)
                .build();
        try {
            isCreated = roomDao.create(room);
        } catch (DaoException e) {
            logger.error("Try to createRoom was failed " + e);
            throw new ServiceException("Try to createRoom  was failed", e);
        }
        return isCreated;
    }

    @Override
    public boolean updateRoom(Map<String, String> roomData) throws ServiceException {
        boolean isUpdated = false;
        RoomValidator validator = RoomValidatorImpl.getInstance();
        if (!validator.validateRoomData(roomData)) {
            logger.info("Not valid data");
            return isUpdated;
        }
        int number = Integer.parseInt(roomData.get(ROOM_NUMBER_SES));
        int sleepingPlace = Integer.parseInt(roomData.get(SLEEPING_PLACE_SES));
        BigDecimal price = new BigDecimal(roomData.get(PRICE_SES));
        boolean visible = roomData.get(VISIBLE_SES) != null
                ? Boolean.parseBoolean(roomData.get(VISIBLE_SES))
                : Boolean.FALSE;
        logger.debug("visible" + visible);
        try {
            long roomId = Long.parseLong(roomData.get(ROOM_ID_SES));
            BigDecimal rating = new BigDecimal(roomData.get(RATING_SES));
            Room room = Room.newBuilder()
                    .setEntityId(roomId)
                    .setNumber(number)
                    .setSleepingPlace(sleepingPlace)
                    .setPricePerDay(price)
                    .setVisible(visible)
                    .setRating(rating)
                    .build();

            isUpdated = roomDao.update(room);
        } catch (NumberFormatException e) {
            logger.info("Not valid room id");
        } catch (DaoException e) {
            logger.error("Try to updateRoom was failed " + e);
            throw new ServiceException("Try to updateRoom  was failed", e);
        }
        return isUpdated;
    }

    private void preparePaginationData(Map<String, Long> paginationData) throws DaoException {
        int num = roomDao.findNumberOfVisibleRoom();
        paginationData.put(ALL_SHEET_SES, Long.valueOf(num));
        paginationData.put(CURRENT_SHEET_SES, 0L);
        paginationData.put(LAST_ID_SES, 0L);
        paginationData.put(FIRST_ID_SES, 0L);
    }

    private void refreshPaginationData(String direction, Map<String, Long> paginationData, List<Room> rooms) {
        if (!rooms.isEmpty()) {
            long newFirst = rooms.get(0).getEntityId();
            long newLast = rooms.get(rooms.size() - 1).getEntityId();
            paginationData.put(FIRST_ID_SES, newFirst);
            paginationData.put(LAST_ID_SES, newLast);
            long currentSheet = paginationData.get(CURRENT_SHEET_SES);
            paginationData.put(CURRENT_SHEET_SES, currentSheet + Long.parseLong(direction));
        }
    }
}
