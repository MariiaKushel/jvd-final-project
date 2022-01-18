package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.*;
import by.javacourse.hotel.util.CurrentPageExtractor;
import by.javacourse.hotel.util.ImageEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;

public class FindRoomByIdCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        ImageService imageService = provider.getImageService();
        DescriptionService descriptionService = provider.getDescriptionService();
        ReviewService reviewService = provider.getReviewService();

        HttpSession session = request.getSession();
        long roomId = Long.parseLong(request.getParameter(RequestParameter.ROOM_ID));
        Optional<Room> room = Optional.empty();

        CommandResult commandResult = null;

        try {
            room = roomService.findRoomById(roomId);
            if (room.isPresent()) {
                List<Image> images = imageService.findImagesByRoomId(roomId);
                List<String> imageList = images.stream().map(i -> ImageEncoder.encode(i.getImageContent())).toList();

                Optional<Description> description = descriptionService.findDescriptionByRoomId(roomId);
                if(description.isPresent()){
                    request.setAttribute(RequestAttribute.DESCRIPTION, description.get());
                }

                List<Review> reviews = reviewService.findReviewsByRoomId(roomId);

                request.setAttribute(RequestAttribute.ROOM, room.get());
                request.setAttribute(RequestAttribute.IMAGE_LIST, imageList);
                request.setAttribute(RequestAttribute.REVIEW_LIST, reviews);

                request.setAttribute(RequestAttribute.TEMP_DATE_FROM, request.getParameter(RequestParameter.DATE_FROM));
                request.setAttribute(RequestAttribute.TEMP_DATE_TO, request.getParameter(RequestParameter.DATE_TO));

                session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
                commandResult = new CommandResult(PagePath.ROOM_PAGE, FORWARD);
            } else {
                request.setAttribute(RequestAttribute.ROOM_NOT_FOUND,true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
                commandResult = new CommandResult(PagePath.ROOM_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Try to execute FindRoomByIdCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
