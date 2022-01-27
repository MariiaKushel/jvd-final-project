package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountService {
    List<Discount> findAllDiscount() throws ServiceException;

    List<Discount> findDiscountByRate(Map<String, String> parameters) throws ServiceException;

    Optional<Discount> findDiscountById(long discountId) throws ServiceException;
}
