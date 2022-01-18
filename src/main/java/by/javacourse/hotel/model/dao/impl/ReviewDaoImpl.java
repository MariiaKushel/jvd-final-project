package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.dao.ReviewDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.DescriptionMapper;
import by.javacourse.hotel.model.dao.mapper.impl.ReviewMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_REVIEW = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews""";

    private static final String BY_ID = " WHERE review_id=? LIMIT 1";
    private static final String BY_ROOM_ID = """ 
             JOIN hotel.room_orders
            ON review_id=room_order_id AND room_id=?
            JOIN hotel.users
            ON hotel.room_orders.user_id=hotel.users.user_id""";

    @Override
    public List<Review> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Review review) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Review review) throws DaoException {
        return false;
    }

    @Override
    public Optional<Review> update(Review review) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Review> findReviewsByRoomId(long roomId) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_REVIEW + BY_ROOM_ID)) {
            statement.setLong(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                reviews = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findReviewById from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findReviewById from table hotel.reviews was failed", e);
        }
        return reviews;
    }
}
