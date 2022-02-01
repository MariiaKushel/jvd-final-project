package by.javacourse.hotel.controller.command.impl.admin.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.*;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DescriptionService;
import by.javacourse.hotel.model.service.ImageService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.ImageEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.ROOM_ID;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToUpdateRoomPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(UPDATE_ROOM_RESULT);
        session.removeAttribute(UPDATE_DESCRIPTION_RESULT);
        session.removeAttribute(UPDATE_IMAGE_RESULT);
        session.removeAttribute(UPLOAD_RESULT);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        DescriptionService descriptionService = provider.getDescriptionService();
        ImageService imageService = provider.getImageService();

        String roomId = request.getParameter(ROOM_ID);
        CommandResult commandResult = null;
        try {
            Optional<Room> optRoom = roomService.findRoomById(roomId);
            if (optRoom.isPresent()) {
                Room room = optRoom.get();
                Map<String, String> roomData = createRoomDataMap(room);
                session.setAttribute(ROOM_DATA_SES, roomData);

                Optional<Description> optDescription = descriptionService.findDescriptionByRoomId(room.getEntityId());
                Map<String, String> descriptionData = new HashMap<>();
                if (optDescription.isPresent()) {
                    Description description = optDescription.get();
                    descriptionData = createDescriptionDataMap(description);
                } else {
                    descriptionData.put(ROOM_ID_SES, String.valueOf(room.getEntityId()));
                }
                session.setAttribute(DESCRIPTION_DATA_SES, descriptionData);

                List<Image> images = imageService.findImagesByRoomId(room.getEntityId());
                Map<Long, String> imageData = createImageDataMap(images);
                session.setAttribute(IMAGE_DATA_SES, imageData);

                Optional<Image> preview = images.stream().filter(i -> i.isPreview()).findFirst();
                if (preview.isPresent()) {
                    session.setAttribute(PREVIEW_MARKER_SES, preview.get().getEntityId());
                }

            } else {
                session.setAttribute(NOT_FOUND_SES, true);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToUpdateRoomPageCommand was failed " + e);
             throw new CommandException("Try to execute GoToUpdateRoomPageCommand was failed ", e);
        }
        return commandResult;
    }

    private Map<String, String> createRoomDataMap(Room room) {
        Map<String, String> roomData = new HashMap<>();
        roomData.put(ROOM_ID_SES, String.valueOf(room.getEntityId()));
        roomData.put(ROOM_NUMBER_SES, String.valueOf(room.getNumber()));
        roomData.put(SLEEPING_PLACE_SES, String.valueOf(room.getSleepingPlace()));
        roomData.put(PRICE_SES, room.getPricePerDay().toString());
        roomData.put(VISIBLE_SES, String.valueOf(room.isVisible()));
        roomData.put(RATING_SES, room.getRating().toString());
        return roomData;
    }

    private Map<String, String> createDescriptionDataMap(Description description) {
        Map<String, String> descriptionData = new HashMap<>();
        descriptionData.put(DESCRIPTION_ID_SES, String.valueOf(description.getEntityId()));
        descriptionData.put(DESCRIPTION_RU_SES, description.getDescriptionRu());
        descriptionData.put(DESCRIPTION_EN_SES, description.getDescriptionEn());
        return descriptionData;
    }

    private Map<Long, String> createImageDataMap(List<Image> images) {
        Map<Long, String> imageData = new HashMap<>();

        imageData = images.stream()
                .collect(Collectors
                        .toMap(i -> Long.valueOf(i.getEntityId()), i -> ImageEncoder.encode(i.getImageContent())));

        imageData.forEach((k, v) -> System.out.println(k));
        return imageData;
    }
}
