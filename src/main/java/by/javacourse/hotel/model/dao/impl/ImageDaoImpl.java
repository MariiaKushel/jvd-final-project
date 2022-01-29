package by.javacourse.hotel.model.dao.impl;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.dao.ImageDao;
import by.javacourse.hotel.model.dao.mapper.Mapper;
import by.javacourse.hotel.model.dao.mapper.impl.ImageMapper;
import by.javacourse.hotel.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class ImageDaoImpl implements ImageDao {
    static Logger logger = LogManager.getLogger();

    private static final String SQL_INSERT_IMAGE = """
            INSERT INTO hotel.images (room_id, image, preview) 
            VALUES (?,?,?)""";

    private static final String SQL_DELETE_IMAGE = "DELETE FROM hotel.images WHERE image_id=?";

    private static final String SQL_SELECT_IMAGE = """
            SELECT image_id, room_id, image, preview
            FROM hotel.images""";

    private static final String BY_ID = " WHERE image_id=? LIMIT 1";
    private static final String BY_ROOM_ID = " WHERE room_id=?";
    private static final String BY_ROOM_ID_FIRST = " WHERE room_id=? LIMIT 1";

    private static final String SQL_SELECT_IMAGE_BY_VISIBLE_ROOM = """
            SELECT image_id, hotel.images.room_id, image, preview
            FROM hotel.images
            JOIN hotel.rooms 
            ON hotel.images.room_id=hotel.rooms.room_id 
            AND visible=true 
            AND preview=true""";

    private static final String SQL_UPDATE_OLD_PREVIEW = """
            UPDATE hotel.images SET preview=0 WHERE room_id=? AND preview=1""";

    private static final String SQL_UPDATE_NEW_PREVIEW = """
            UPDATE hotel.images SET preview=1 WHERE image_id=?""";

    @Override
    public List<Image> findAll() throws DaoException {
        List<Image> images = new ArrayList<>();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_IMAGE)) {
            images = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findAll from table hotel.images was failed" + e);
            throw new DaoException("SQL request findAll from table hotel.images was failed", e);
        }
        return images;
    }

    @Override
    public boolean delete(Image image) throws DaoException {
        int deletedRows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_IMAGE)) {
            statement.setLong(1, image.getEntityId());
            deletedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request delete from table hotel.images was failed" + e);
            throw new DaoException("SQL request delete from table hotel.images was failed", e);
        }
        return deletedRows == 1;
    }

    @Override
    public boolean create(Image image) throws DaoException {
        int insertedRows = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_IMAGE)) {
            statement.setLong(1, image.getRoomId());
            statement.setBytes(2, image.getImageContent());
            statement.setBoolean(3, image.isPreview());
            insertedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL request create from table hotel.images was failed" + e);
            throw new DaoException("SQL request create from table hotel.images was failed", e);
        }
        return insertedRows == 1;
    }

    @Override
    public Optional<Image> update(Image image) throws DaoException {
        Optional<Image> oldImage = Optional.empty();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE + BY_ID,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, image.getEntityId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    oldImage = mapper.retrieve(resultSet).stream().findFirst();
                    resultSet.first();
                    resultSet.updateLong(ROOM_ID, image.getRoomId());
                    Blob blob = new SerialBlob(image.getImageContent());
                    resultSet.updateBlob(IMAGE, blob);
                    resultSet.updateRow();
                }
            }
        } catch (SQLException e) {
            logger.error("SQL request update from table hotel.images was failed" + e);
            throw new DaoException("SQL request update from table hotel.images was failed", e);
        }
        return oldImage;
    }


    @Override
    public Optional<Image> findImageById(long imageId) throws DaoException {
        Optional<Image> image = Optional.empty();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE + BY_ID)) {
            statement.setLong(1, imageId);
            try (ResultSet resultSet = statement.executeQuery()) {
                image = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findImageById from table hotel.images was failed" + e);
            throw new DaoException("SQL request findImageById from table hotel.images was failed", e);
        }
        return image;
    }

    @Override
    public List<Image> findImageByRoomId(long roomId) throws DaoException {
        List<Image> images = new ArrayList<>();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE + BY_ROOM_ID)) {
            statement.setLong(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                images = mapper.retrieve(resultSet);
            }
        } catch (SQLException e) {
            logger.error("SQL request findImageByRoomId from table hotel.images was failed" + e);
            throw new DaoException("SQL request findImageByRoomId from table hotel.images was failed", e);
        }
        return images;
    }

    @Override
    public Optional<Image> findFirstImageByRoomId(long roomId) throws DaoException {
        Optional<Image> image = Optional.empty();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE + BY_ROOM_ID_FIRST)) {
            statement.setLong(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                image = mapper.retrieve(resultSet).stream().findFirst();
            }
        } catch (SQLException e) {
            logger.error("SQL request findImageById from table hotel.images was failed" + e);
            throw new DaoException("SQL request findImageById from table hotel.images was failed", e);
        }
        return image;
    }

    @Override
    public List<Image> findPreviewByVisibleRoom() throws DaoException {
        List<Image> images = new ArrayList<>();
        Mapper mapper = ImageMapper.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_IMAGE_BY_VISIBLE_ROOM)) {
            images = mapper.retrieve(resultSet);
        } catch (SQLException e) {
            logger.error("SQL request findImageByRoomId from table hotel.images was failed" + e);
            throw new DaoException("SQL request findImageByRoomId from table hotel.images was failed", e);
        }
        return images;
    }

    @Override
    public boolean changePreview(long newPreviewId, long roomId) throws DaoException {
        boolean result = false;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(SQL_UPDATE_OLD_PREVIEW);
            statement.setLong(1, roomId);
            statement.executeUpdate();

            statement = connection.prepareStatement(SQL_UPDATE_NEW_PREVIEW);
            statement.setLong(1, newPreviewId);
            result = statement.executeUpdate() == 1;

            if (result) {
                connection.commit();
            }

        } catch (SQLException e) {
            logger.error("SQL request changePreview from table hotel.room_orders was failed " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback changePreview from table hotel.room_orders was failed " + e);
                throw new DaoException("Rollback changePreview from table hotel.room_orders was failed", e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection or statement close was failed" + e);
                throw new DaoException("Connection or statement close was failed", e);
            }
        }
        return result;
    }

}
