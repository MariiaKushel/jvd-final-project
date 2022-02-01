package by.javacourse.hotel.model.dao.mapper;

import by.javacourse.hotel.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * {@code Mapper} interface represent functional of retrieving object extends {@link Entity} from {@link ResultSet}
 */
public interface Mapper <T extends Entity> {

    /**
     * Retrieve object extends {@link Entity}
     * @param resultSet - execution result sql instruction
     * @return object extends {@link Entity}
     */
    List<T> retrieve (ResultSet resultSet) throws SQLException;
}
