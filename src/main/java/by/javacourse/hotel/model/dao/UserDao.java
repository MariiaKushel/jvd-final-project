package by.javacourse.hotel.model.dao;

import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.model.entity.User;

public interface UserDao extends BaseDao<Long, User> {
    boolean isLoginExsist(String login) throws DaoException;

    boolean isUserExsist(String login, String password) throws DaoException;
}
