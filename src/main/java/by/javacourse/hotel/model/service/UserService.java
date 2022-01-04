package by.javacourse.hotel.model.service;

import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> createNewAccount(Map<String, String> userData, String password) throws ServiceException;

    Optional<User> authenticate(String email, String password) throws ServiceException;

    Optional<User> updatePersonalData(User user, String password) throws ServiceException;
}
