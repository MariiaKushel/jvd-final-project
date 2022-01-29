package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.ORDER_STATUS;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UpdateReviewCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService reviewService = provider.getReviewService();

        long reviewId = ((Review) session.getAttribute(REVIEW_SES)).getEntityId();
        boolean oldHiddenMark = ((Review) session.getAttribute(REVIEW_SES)).isHidden();
        CommandResult commandResult = null;
        try {
            boolean result = reviewService.updateHidden(reviewId, oldHiddenMark);
            session.setAttribute(UPDATE_ORDER_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CancelOrderCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

}
