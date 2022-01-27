package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class RoomOrderMapper implements Mapper<RoomOrder> {
    private static final RoomOrderMapper instance = new RoomOrderMapper();

    private RoomOrderMapper() {

    }

    public static RoomOrderMapper getInstance() {
        return instance;
    }

    @Override
    public List<RoomOrder> retrieve(ResultSet resultSet) throws SQLException {
       List<RoomOrder> orders = new ArrayList<>();
        while (resultSet.next()) {
            RoomOrder order = RoomOrder.newBuilder()
                    .setEntityId(resultSet.getLong(ROOM_ORDER_ID))
                    .setUserId(resultSet.getLong(USER_ID))
                    .setRoomId(resultSet.getLong(ROOM_ID))
                    .setDate(resultSet.getDate(ROOM_ORDER_DATE).toLocalDate())
                    .setFrom(resultSet.getDate(FROM).toLocalDate())
                    .setTo(resultSet.getDate(TO).toLocalDate())
                    .setStatus(RoomOrder.Status.valueOf(resultSet.getString(ROOM_ORDER_STATUS).toUpperCase()))
                    .setAmount(resultSet.getBigDecimal(AMOUNT))
                    .setPrepayment(resultSet.getBoolean(PREPAYMENT))
                    .build();
            System.out.println("order" + order);
            orders.add(order);
        }
        return orders;
    }
}
