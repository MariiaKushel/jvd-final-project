package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

/**
 * {@code DescriptionMapper} class implements functional of {@link Mapper}
 */
public class DescriptionMapper implements Mapper<Description> {
    private static final DescriptionMapper instance = new DescriptionMapper();

    private DescriptionMapper() {

    }

    public static DescriptionMapper getInstance() {
        return instance;
    }

    @Override
    public List<Description> retrieve(ResultSet resultSet) throws SQLException {
        List<Description> descriptions = new ArrayList<>();
        while (resultSet.next()) {
            Description description = Description.newBuilder()
                    .setEntityId(resultSet.getLong(DESCRIPTION_ID))
                    .setDescriptionRu(resultSet.getString(DESCRIPTION_RU))
                    .setDescriptionEn(resultSet.getString(DESCRIPTION_EN))
                    .build();
            descriptions.add(description);
        }
        return descriptions;
    }
}
