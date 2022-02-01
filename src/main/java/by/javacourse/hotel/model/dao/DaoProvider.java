package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.model.dao.impl.*;

/**
 * {@code DaoProvider} class hold and provide instance of all classes extends {@link BaseDao}
 */
public class DaoProvider {

    private UserDao userDao = new UserDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();
    private ImageDao imageDao = new ImageDaoImpl();
    private DescriptionDao descriptionDao = new DescriptionDaoImpl();
    private ReviewDao reviewDao = new ReviewDaoImpl();
    private RoomOrderDao roomOrderDao = new RoomOrderDaoImpl();
    private DiscountDao discountDao = new DiscountDaoImpl();

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

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

    public DescriptionDao getDescriptionDao() {
        return descriptionDao;
    }

    public ReviewDao getReviewDao() {
        return reviewDao;
    }

    public RoomOrderDao getRoomOrderDao() {
        return roomOrderDao;
    }

    public DiscountDao getDiscountDao() {
        return discountDao;
    }
}
