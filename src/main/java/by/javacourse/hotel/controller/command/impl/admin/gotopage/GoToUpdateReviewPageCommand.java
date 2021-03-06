package by.javacourse.hotel.controller.command.impl.admin.gotopage;

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

import java.util.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToUpdateReviewPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(WRONG_REVIEW_ID_SES);
        session.removeAttribute(UPDATE_REVIEW_RESULT);

        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService service = provider.getReviewService();
        String reviewId = request.getParameter(REVIEW_ID);

        CommandResult commandResult = null;
        try {
            Optional<Review> optReview = service.findReviewById(reviewId);
            if (optReview.isPresent()) {
                Review review = optReview.get();
                session.setAttribute(REVIEW_SES, review);
            } else {
                session.setAttribute(WRONG_REVIEW_ID_SES, true);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToUpdateReviewPageCommand was failed " + e);
             throw new CommandException("Try to execute GoToUpdateReviewPageCommand was failed ", e);
        }
        return commandResult;
    }

}
