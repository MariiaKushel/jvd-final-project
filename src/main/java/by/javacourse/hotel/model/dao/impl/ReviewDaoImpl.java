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

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_REVIEW = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews""";

    private static final String BY_ROOM_ID = """ 
             JOIN hotel.room_orders ON review_id=room_order_id AND room_id=?
            JOIN hotel.users ON hotel.room_orders.user_id=hotel.users.user_id""";

    private static final String BY_DATE_RANGE = " WHERE hotel.reviews.date BETWEEN ? AND ? ORDER BY hotel.reviews.date DESC";

    private static final String SQL_SELECT_REVIEW_BY_USER_ID = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews
            JOIN hotel.room_orders ON review_id=room_order_id AND hotel.room_orders.user_id=?
            JOIN hotel.users ON hotel.room_orders.user_id=hotel.users.user_id""";

    private static final String SQL_SELECT_REVIEW_BY_ID = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews
            JOIN hotel.room_orders ON review_id=room_order_id AND review_id=?
            JOIN hotel.users ON hotel.room_orders.user_id=hotel.users.user_id""";

    //FIXME
    private static final String SQL_SELECT_REVIEW_BY_USER_ID_FROM_DATE = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews
            JOIN hotel.room_orders ON review_id=room_order_id AND hotel.room_orders.user_id=?
            AND hotel.room_orders.date>?
            JOIN hotel.users ON hotel.room_orders.user_id=hotel.users.user_id""";

    private static final String SQL_SELECT_ALL_REVIEW = """
            SELECT review_id, hotel.reviews.date, room_mark, review, hidden, hotel.users.name
            FROM hotel.reviews
            JOIN hotel.room_orders ON review_id=room_order_id
            JOIN hotel.users ON hotel.room_orders.user_id=hotel.users.user_id""";

    private static final String SQL_INSERT_REVIEW = """
            INSERT INTO hotel.reviews (review_id, hotel.reviews.date, room_mark, review, hidden)
            VALUES(?,?,?,?,?)""";

    private static final String SQL_UPDATE_REVIEW_HIDDEN = """
            UPDATE hotel.reviews SET hidden=? WHERE review_id=?;
            """;


    @Override
    public List<Review> findAll() throws DaoException {
        List<Review> reviews = new ArrayList<>();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_REVIEW)) {
            reviews = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findAll from table hotel.reviews was failed", e);
        }
        return reviews;
    }

    @Override
    public boolean delete(Review review) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Review review) throws DaoException {
        int insertedRows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_REVIEW)) {
            statement.setLong(1, review.getEntityId());
            statement.setDate(2, Date.valueOf(review.getDate()));
            statement.setInt(3, review.getRoomMark());
            statement.setString(4, review.getReviewContent());
            statement.setBoolean(5, review.isHidden());
            insertedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request create from table hotel.reviews was failed", e);
        }
        return insertedRows == 1;
    }

    @Override
    public Optional<Review> update(Review review) throws DaoException {
        return Optional.empty();
    }
    @Override
    public boolean update1(Review review) throws DaoException {
        int updatedRows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REVIEW_HIDDEN)) {
            statement.setBoolean(1, review.isHidden());
            statement.setLong(2, review.getEntityId());
            updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request update1 from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request update1 from table hotel.reviews was failed", e);
        }
        return updatedRows == 1;
    }

    @Override
    public List<Review> findReviewByRoomId(long roomId) throws DaoException {
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

    @Override
    public List<Review> findReviewByDateRange(LocalDate from, LocalDate to) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_REVIEW + BY_DATE_RANGE)) {
            statement.setDate(1, Date.valueOf(from));
            statement.setDate(2, Date.valueOf(to));
            try (ResultSet resultSet = statement.executeQuery()) {
                reviews = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findReviewByDateRange from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findReviewByDateRange from table hotel.reviews was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewByUserId(long userId) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_REVIEW_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                reviews = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findReviewByUserId from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findReviewByUserId from table hotel.reviews was failed", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewByUserIdFromDate(long userId, LocalDate date) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_REVIEW_BY_USER_ID_FROM_DATE)) {
            statement.setLong(1, userId);
            statement.setDate(2, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                reviews = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findReviewByUserIdFromDate from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findReviewByUserIdFromDate from table hotel.reviews was failed", e);
        }
        return reviews;
    }

    @Override
    public Optional<Review> findReviewById(long reviewId) throws DaoException {
        Optional<Review> reviews = Optional.empty();
        Mapper mapper = ReviewMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_REVIEW_BY_ID)) {
            statement.setLong(1, reviewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                reviews = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findReviewById from table hotel.reviews was failed" + e);
            throw new DaoException("SQL request findReviewById from table hotel.reviews was failed", e);
        }
        return reviews;
    }
}
