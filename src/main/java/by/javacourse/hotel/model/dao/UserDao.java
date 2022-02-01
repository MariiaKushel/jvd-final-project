package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code UserDao} class implements functional of {@link BaseDao}
 */
public interface UserDao extends BaseDao<Long, User> {

    /**
     * Find user with that email in database
     * @param email - user email
     * @return true - if user with that email was found and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean isEmailExist(String email) throws DaoException;

    /**
     * Find user by id and password
     * @param email - user email
     * @param password - encoded password
     * @return an Optional describing user, or an empty Optional if user not found
     * @throws DaoException - if request from database was failed
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * Update user password
     * @param userId - user id
     * @param newPassword - new encoded password
     * @return true - if user password was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean changePassword(long userId, String newPassword) throws DaoException;

    /**
     * Create new user with password in database
     * @param user - user
     * @param password - encoded password
     * @return true - if user password was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean createUserWithPassword(User user, String password) throws DaoException;

    /**
     * Find user balanse by user id
     * @param userId - user id
     * @return value of user balance
     * @throws DaoException - if request from database was failed
     */
    BigDecimal findBalanceByUserId(long userId) throws DaoException;

    /**
     * Find user by parameters
     * @param status - room visible
     * @param emailPart - room visible
     * @param phonePart - room visible
     * @param namePart - room visible
     * @return list of user or empty list if user not found
     * @throws DaoException - if request from database was failed
     */
    List<User> findUserByParameter(String status, String emailPart, String phonePart, String namePart) throws DaoException;

    /**
     * Update user balance
     * @param userId - user id
     * @param amount - amount value which should write off the balance
     * @return true - if balance was updated and false - if was not
     * @throws DaoException - if request from database was failed
     */
    boolean updateBalance(long userId, BigDecimal amount) throws DaoException;

}
