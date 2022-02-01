package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ImageService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class UploadImageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        ImageService imageService = provider.getImageService();

        CommandResult commandResult = null;
        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                String roomId = request.getParameter(ROOM_ID);
                boolean result = imageService.createImage(newImage, roomId);
                session.setAttribute(UPLOAD_RESULT, result);
                session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
                commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
            }
        } catch (IOException | ServletException | ServiceException e) {
            logger.error("Try to execute UploadImageCommand was failed" + e);
             throw new CommandException("Try to execute UploadImageCommand was failed", e);
        }

        return commandResult;
    }

}
