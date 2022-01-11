package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class ImageMapper implements Mapper<Image> {
    private static final ImageMapper instance = new ImageMapper();

    private ImageMapper() {

    }

    public static ImageMapper getInstance() {
        return instance;
    }

    @Override
    public List<Image> retrieve(ResultSet resultSet) throws SQLException {
        List<Image> images = new ArrayList<>();
        while (resultSet.next()) {

            Image.Builder builder = Image.newBuilder()
                    .setEntityId(resultSet.getLong(IMAGE_ID))
                    .setRoomId(resultSet.getLong(ROOM_ID))
                    .setPreview(resultSet.getBoolean(PREVIEW));
            Blob blob = resultSet.getBlob(IMAGE);
            if (blob != null) {
                byte[] imageContent = blob.getBytes(1, (int) blob.length());
                builder.setImageContent(imageContent);
            }
            Image image = builder.build();
            images.add(image);
        }
        return images;
    }
}
