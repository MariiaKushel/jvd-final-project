package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.*;

public class RoomMapper implements Mapper<Room> {
    private static final RoomMapper instance = new RoomMapper();

    private RoomMapper(){

    }
    public static RoomMapper getInstance(){
        return instance;
    }

    @Override
    public List<Room> retrieve(ResultSet resultSet) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (resultSet.next()){
            Room room = Room.newBuilder()
                    .setEntityId(resultSet.getLong(ROOM_ID))
                    .setNumber(resultSet.getInt(NUMBER))
                    .setSleepingPlace(resultSet.getInt(SLEEPING_PLACE))
                    .setPricePerDay(resultSet.getBigDecimal(PRICE_PER_DAY))
                    .setRating(resultSet.getBigDecimal(RATING))
                    .setVisible(resultSet.getBoolean(VISIBLE))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .build();
            rooms.add(room);
        }
        return rooms;
    }
}
