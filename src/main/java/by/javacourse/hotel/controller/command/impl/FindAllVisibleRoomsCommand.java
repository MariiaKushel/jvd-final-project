package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ImageService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import by.javacourse.hotel.util.ImageEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;

public class FindAllVisibleRoomsCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        ImageService imageService = provider.getImageService();

        CommandResult commandResult = null;
        try {
            List<Room> rooms = roomService.findAllVisibleRooms();
            List<Image> images = imageService.findPreviewByVisibleRoom();

            Map<Long, String> previews = new HashMap<>();
            for (Image image : images) {
               byte[] imageContent = image.getImageContent();
               String imageAsString = ImageEncoder.encode(imageContent);
               previews.put(image.getRoomId(), imageAsString);
            }

            request.setAttribute(RequestAttribute.ROOM_LIST, rooms);
            request.setAttribute(RequestAttribute.PREVIEW_MAP, previews);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));

            commandResult = new CommandResult(PagePath.SHOW_ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindAllVisibleRoomsCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }

}
