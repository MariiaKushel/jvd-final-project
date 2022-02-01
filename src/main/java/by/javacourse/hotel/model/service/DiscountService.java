package by.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.Entity;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code DiscountService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.Discount}
 */
public interface DiscountService {

    /**
     * Find all discount
     * @return discount list or empty list if discount not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Discount> findAllDiscount() throws ServiceException;

    /**
     * Find discount by rate
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return discount list or empty list if discount not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<Discount> findDiscountByRate(Map<String, String> parameters) throws ServiceException;

    /**
     * Find discount by id
     * @param discountId - discount id
     * @return an Optional describing discount, or an empty Optional if discount not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<Discount> findDiscountById(String discountId) throws ServiceException;

    /**
     * Find discount by user id
     * @param userId - user id
     * @return an Optional describing discount, or an empty Optional if discount not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<Discount> findDiscountByUserId(String userId) throws ServiceException;

    /**
     * Update discount
     * @param discountData - map with discount data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if discount was updated and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updateDiscount(Map<String, String> discountData) throws ServiceException;

    /**
     * Create discount
     * @param discountData - map with discount data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if discount was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createDiscount(Map<String, String> discountData) throws ServiceException;

    /**
     * Create discount
     * @param discount - discount object
     * @return true - if discount was created and false - if was not
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean removeDiscount(Discount discount) throws ServiceException;
}
