package by.javacourse.hotel.controller.command.impl.common;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.CommandException;
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


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.DATE_FROM;
import static by.javacourse.hotel.controller.command.RequestParameter.DATE_TO;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;
import static by.javacourse.hotel.controller.command.SessionAttribute.NOT_FOUND_SES;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class FindRoomByIdCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        session.removeAttribute(NOT_FOUND_SES);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        ImageService imageService = provider.getImageService();
        DescriptionService descriptionService = provider.getDescriptionService();
        ReviewService reviewService = provider.getReviewService();

        String roomIdStr = request.getParameter(RequestParameter.ROOM_ID);
        CommandResult commandResult = null;

        try {
            Optional<Room>  room = roomService.findRoomById(roomIdStr);
            if (room.isPresent()) {
                long roomId = Long.parseLong(roomIdStr);
                List<Image> images = imageService.findImagesByRoomId(roomId);
                List<String> imageList = images.stream().map(i -> ImageEncoder.encode(i.getImageContent())).toList();

                Optional<Description> description = descriptionService.findDescriptionByRoomId(roomId);
                if(description.isPresent()){
                    request.setAttribute(DESCRIPTION_ATR, description.get());
                }

                List<Review> reviews = reviewService.findReviewsByRoomId(roomId);

                request.setAttribute(ROOM_ATR, room.get());
                request.setAttribute(IMAGE_LIST_ATR, imageList);
                request.setAttribute(REVIEW_LIST_ATR, reviews);

                request.setAttribute(DATE_FROM_ATR, request.getParameter(DATE_FROM));//fixme
                request.setAttribute(DATE_TO_ATR, request.getParameter(DATE_TO));//fixme
            } else {
                session.setAttribute(NOT_FOUND_SES,true);
            }
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindRoomByIdCommand was failed " + e);
             throw new CommandException("Try to execute FindRoomByIdCommand was failed ", e);
        }
        return commandResult;
    }
}
