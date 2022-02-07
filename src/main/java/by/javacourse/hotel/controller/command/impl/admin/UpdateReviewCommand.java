package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class UpdateReviewCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService reviewService = provider.getReviewService();

        long reviewId = ((Review) session.getAttribute(REVIEW_SES)).getEntityId();
        boolean oldHiddenMark = ((Review) session.getAttribute(REVIEW_SES)).isHidden();
        CommandResult commandResult = null;
        try {
            boolean result = reviewService.updateHidden(reviewId, oldHiddenMark);
            session.setAttribute(UPDATE_REVIEW_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CancelOrderCommand was failed" + e);
             throw new CommandException("Try to execute CancelOrderCommand was failed", e);
        }
        return commandResult;
    }

}
