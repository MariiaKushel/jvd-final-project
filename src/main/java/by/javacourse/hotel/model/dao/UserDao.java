package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    boolean isEmailExist(String email) throws DaoException;

    boolean isUserExist(String email, String password) throws DaoException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    boolean changePassword(String email, String newPassword) throws DaoException;
}