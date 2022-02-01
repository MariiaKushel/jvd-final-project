package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DescriptionService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UpdateDescriptionCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> descriptionData = (Map<String, String>) session.getAttribute(DESCRIPTION_DATA_SES);
        System.out.println("descriptionData0 "+descriptionData);
        cleanWrongMessage(descriptionData);
        updateDescriptionDataFromRequest(request, descriptionData);

        ServiceProvider provider = ServiceProvider.getInstance();
        DescriptionService descriptionService = provider.getDescriptionService();

        CommandResult commandResult = null;
        try {
            System.out.println("descriptionData1 "+descriptionData);
            int sizeBefore = descriptionData.size();
            boolean result = descriptionService.updateDescription(descriptionData);
            System.out.println("descriptionData2 "+descriptionData);
            int sizeAfter = descriptionData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(DESCRIPTION_DATA_SES);
                session.setAttribute(UPDATE_DESCRIPTION_RESULT, result);
            } else {
                session.setAttribute(DESCRIPTION_DATA_SES, descriptionData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_ROOM_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_ROOM_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateDescriptionCommand was failed" + e);
             throw new CommandException("Try to execute UpdateDescriptionCommand was failed", e);
        }
        return commandResult;
    }

    private void updateDescriptionDataFromRequest(HttpServletRequest request, Map<String, String> descriptionData) {
        descriptionData.put(DESCRIPTION_RU_SES, request.getParameter(DESCRIPTION_RU));
        descriptionData.put(DESCRIPTION_EN_SES, request.getParameter(DESCRIPTION_EN));
    }

    private void cleanWrongMessage(Map<String, String> descriptionData) {
        descriptionData.remove(WRONG_DESCRIPTION_EN_SES);
        descriptionData.remove(WRONG_DESCRIPTION_RU_SES);
    }
}
