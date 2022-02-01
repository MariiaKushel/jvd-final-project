package by.javacourse.hotel.model.service;

import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code UserService} interface represent functional business logic
 * for work with class {@link by.javacourse.hotel.entity.User}
 */
public interface UserService {

    /**
     * Create user
     * @param userData - map with user data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if user was created and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean createNewAccount(Map<String, String> userData) throws ServiceException;

    /**
     * Authenticate user in system
     * @param userData - map with user data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return an Optional describing user, or an empty Optional if user not found or email or password wrong
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<User> authenticate(Map<String, String> userData) throws ServiceException;

    /**
     * Update user
     * @param userData - map with user data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if user was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean updatePersonalData(Map<String, String> userData) throws ServiceException;

    /**
     * Find user by id
     * @param userId - user id
     * @return an Optional describing user, or an empty Optional if user not found
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    Optional<User> findUserById(String userId) throws ServiceException;

    /**
     * Find user by parameters
     * @param parameters - map of search parameters
     * As key use {@link by.javacourse.hotel.controller.command.RequestAttribute}
     * @return user list or empty list if user not found
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    List<User> findUserByParameter(Map<String, String> parameters) throws ServiceException;

    /**
     * Update user password
     * @param passwordData - map with user data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if user password was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean changePassword(Map<String, String> passwordData) throws ServiceException;

    /**
     * Update user balance
     * @param balanceData - map with user data
     * As key use {@link by.javacourse.hotel.controller.command.SessionAttribute}
     * @return true - if user balance was updated and false - if was not
     * If some data is not valid, wrong data marker add into parameters as well
     * @throws ServiceException - if dao method throw {@link by.javacourse.hotel.exception.DaoException}
     */
    boolean replenishBalance(Map<String, String> balanceData) throws ServiceException;
}

