package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class UserMapper implements Mapper<User> {
    private static final UserMapper instance = new UserMapper();

    private UserMapper() {

    }

    public static UserMapper getInstance() {
        return instance;
    }

    @Override
    public List<User> retrieve(ResultSet resultSet) throws SQLException {
       List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = User.newBuilder()
                    .setEntityId(resultSet.getLong(USER_ID))
                    .setEmail(resultSet.getString(EMAIL))
                    .setName(NAME)
                    .setPhoneNumber(PHONE_NUMBER)
                    .setRole(User.Role.valueOf(resultSet.getString(ROLE).toUpperCase()))
                    .setStatus(User.Status.valueOf(resultSet.getString(USER_STATUS).toUpperCase()))
                    .setDiscountId(resultSet.getLong(DISCOUNT_ID))
                    .setBalance(resultSet.getBigDecimal(BALANCE))
                    .build();
            users.add(user);
        }
        return users;
    }
}
