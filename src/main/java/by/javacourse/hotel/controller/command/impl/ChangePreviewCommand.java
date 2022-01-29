package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ImageService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ChangePreviewCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String roomId = request.getParameter(ROOM_ID);
        String newPreviewId = request.getParameter(PREVIEW);

        ServiceProvider provider = ServiceProvider.getInstance();
        ImageService service = provider.getImageService();

        CommandResult commandResult = null;
        try {
            boolean result = service.changePreview(newPreviewId, roomId);
            session.setAttribute(UPDATE_IMAGE_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateImageCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }


}
