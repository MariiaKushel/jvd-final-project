package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ImageService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UploadImageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        ImageService imageService = provider.getImageService();

        CommandResult commandResult = null;
        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                String roomId = request.getParameter(ROOM_ID);
                boolean result = imageService.addNewImage(newImage, roomId);
                session.setAttribute(UPLOAD_RESULT, result);
                session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
                commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
            }
        } catch (IOException | ServletException | ServiceException e) {
            logger.error("Try to execute UploadImageCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return commandResult;
    }

}
