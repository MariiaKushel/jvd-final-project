package by.javacourse.hotel.controller.command.impl.client;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
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

public class CreateReviewCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> reviewData = (Map<String, String>) session.getAttribute(REVIEW_DATA_SES);

        cleanWrongMessage(reviewData);
        updateReviewDataFromRequest(request, reviewData);

        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService reviewService = provider.getReviewService();

        CommandResult commandResult = null;
        try {
            long roomId = ((Room) session.getAttribute(ROOM_SES)).getEntityId();
            int sizeBefore = reviewData.size();
            boolean result = reviewService.createReview(reviewData, roomId);
            int sizeAfter = reviewData.size();
            if (sizeBefore == sizeAfter){
                session.setAttribute(CREATE_REVIEW_RESULT, result);
                session.removeAttribute(REVIEW_DATA_SES);
            }else{
                session.setAttribute(REVIEW_DATA_SES, reviewData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.CREATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.CREATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CreateReviewCommand was failed" + e);
            throw new CommandException("Try to execute CreateReviewCommand was failed", e);
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
