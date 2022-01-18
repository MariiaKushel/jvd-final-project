package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class ReviewMapper implements Mapper<Review> {
    private static final ReviewMapper instanse = new ReviewMapper();

    private ReviewMapper() {

    }

    public static ReviewMapper getInstance() {
        return instanse;
    }

    @Override
    public List<Review> retrieve(ResultSet resultSet) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            Review review = Review.newBuilder()
                    .setEntityId(resultSet.getLong(REVIEW_ID))
                    .setDate(resultSet.getDate(REVIEW_DATE).toLocalDate())
                    .setRoomMark(resultSet.getInt(ROOM_MARK))
                    .setReviewContent(resultSet.getString(REVIEW_CONTENT))
                    .setHidden(resultSet.getBoolean(HIDDEN))
                    .setAuthor(resultSet.getString(NAME))
                    .build();
            reviews.add(review);
        }
        return reviews;
    }
}
