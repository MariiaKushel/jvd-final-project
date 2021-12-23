package by.javacourse.hotel.model.service;

import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.entity.User;

import java.util.Optional;

public interface UserService {
    boolean register(User user) throws ServiceException;

    boolean authenticate(String login, String password) throws ServiceException; // TODO return User or boolean?

    Optional<User> updatePersonalData(User user) throws ServiceException;
}
