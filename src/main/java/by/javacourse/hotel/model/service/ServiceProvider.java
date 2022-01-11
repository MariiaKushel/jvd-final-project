package by.javacourse.hotel.model.service;

import by.javacourse.hotel.model.service.impl.ImageServiceImpl;
import by.javacourse.hotel.model.service.impl.RoomServiceImpl;
import by.javacourse.hotel.model.service.impl.UserServiceImpl;

public class ServiceProvider {

    private UserService userService = new UserServiceImpl();
    private RoomService roomService = new RoomServiceImpl();
    private ImageService imageService = new ImageServiceImpl();
    //TODO add more services

    private ServiceProvider() {
    }

    private static class ServiceProviderHolder {
        private static final ServiceProvider instance = new ServiceProvider();
    }

    public static ServiceProvider getInstance() {
        return ServiceProviderHolder.instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public RoomService getRoomService(){
        return roomService;
    }

    public ImageService getImageService(){
        return imageService;
    }
}
