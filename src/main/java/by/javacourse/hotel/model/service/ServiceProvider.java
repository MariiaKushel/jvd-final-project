package by.javacourse.hotel.model.service;

import by.javacourse.hotel.model.dao.BaseDao;
import by.javacourse.hotel.model.service.impl.*;

/**
 * {@code ServiceProvider} class hold and provide instance of all service classes
 */
public class ServiceProvider {

    private UserService userService = new UserServiceImpl();
    private RoomService roomService = new RoomServiceImpl();
    private ImageService imageService = new ImageServiceImpl();
    private DescriptionService descriptionService = new DescriptionServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private RoomOrderService roomOrderService = new RoomOrderServiceImpl();
    private DiscountService discountService = new DiscountServiceImpl();

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

    public DescriptionService getDescriptionService(){
        return descriptionService;
    }

    public ReviewService getReviewService(){
        return reviewService;
    }

    public RoomOrderService getRoomOrderService(){
        return roomOrderService;
    }

    public DiscountService getDiscountService(){
        return discountService;
    }
}
