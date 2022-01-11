package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomDao;
import by.javacourse.hotel.model.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    static Logger logger = LogManager.getLogger();

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
}
