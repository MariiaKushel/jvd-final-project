package by.javacourse.hotel.model.dao.mapper;

import by.javacourse.hotel.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper <T extends Entity> {
    List<T> retrieve (ResultSet resultSet) throws SQLException;
}
