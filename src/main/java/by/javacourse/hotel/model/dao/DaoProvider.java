package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.model.dao.impl.ImageDaoImpl;
import by.javacourse.hotel.model.dao.impl.RoomDaoImpl;
import by.javacourse.hotel.model.dao.impl.UserDaoImpl;

public class DaoProvider {

    private UserDao userDao = new UserDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();
    private ImageDao imageDao = new ImageDaoImpl();
    //TODO add more dao

    private DaoProvider() {
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoomDao getRoomDao(){
        return roomDao;
    }

    public ImageDao getImageDao(){
        return imageDao;
    }
}
