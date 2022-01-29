package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.RequestParameter.PASSWORD;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateReviewCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> reviewData = (Map<String, String>) session.getAttribute(REVIEW_DATA_SES);

        cleanWrongMessage(reviewData);
        updateReviewDataFromRequest(request, reviewData);

        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService reviewService = provider.getReviewService();
        RoomService roomService = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            long roomId = ((Room)session.getAttribute(ROOM_SES)).getEntityId();
            boolean result = reviewService.createReview(reviewData, roomId);
            if (result){
                session.setAttribute(CREATE_REVIEW_RESULT, true);
            }
            session.setAttribute(REVIEW_DATA_SES, reviewData);
            session.setAttribute(CURRENT_PAGE, PagePath.CREATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.CREATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CreateReviewCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

    private void updateReviewDataFromRequest(HttpServletRequest request, Map<String, String> reviewData) {
        reviewData.put(MARK_SES, request.getParameter(MARK));
        reviewData.put(CONTENT_SES, request.getParameter(CONTENT));
    }

    private void cleanWrongMessage(Map<String, String> userData) {
        userData.remove(WRONG_CONTENT_SES);
    }
}
