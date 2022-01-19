package by.javacourse.hotel.model.service;

import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {


    boolean createNewAccount(Map<String, String> userData) throws ServiceException;

    Optional<User> authenticate(String email, String password) throws ServiceException;

    Optional<User> updatePersonalData(User user, String password) throws ServiceException;

    int findDiscountByUserId (String userId) throws ServiceException;
}
