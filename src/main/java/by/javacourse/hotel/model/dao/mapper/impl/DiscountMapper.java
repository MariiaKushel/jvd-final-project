package by.javacourse.hotel.model.dao.mapper.impl;

import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javacourse.hotel.model.dao.ColumnName.DISCOUNT_ID;
import static by.javacourse.hotel.model.dao.ColumnName.RATE;

/**
 * {@code DiscountMapper} class implements functional of {@link Mapper}
 */
public class DiscountMapper implements Mapper<Discount> {
    private static final DiscountMapper instance = new DiscountMapper();

    private DiscountMapper() {
    }

    public static DiscountMapper getInstance() {
        return instance;
    }

    @Override
    public List<Discount> retrieve(ResultSet resultSet) throws SQLException {
        List<Discount> discounts = new ArrayList<>();
        while (resultSet.next()) {
            Discount discount = Discount.newBuilder()
                    .setEntityId(resultSet.getLong(DISCOUNT_ID))
                    .setRate(resultSet.getInt(RATE))
                    .build();
            discounts.add(discount);
        }
        return discounts;
    }
}
