package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.DATE_FROM;
import static by.javacourse.hotel.controller.command.RequestParameter.DATE_TO;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class FindReviewByDateRangeCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(DATE_FROM_ATR, request.getParameter(DATE_FROM));
        searchParameters.put(DATE_TO_ATR, request.getParameter(DATE_TO));
        System.out.println("searchParameters 1" +searchParameters);
        ServiceProvider provider = ServiceProvider.getInstance();
        ReviewService service = provider.getReviewService();
        CommandResult commandResult = null;
        try {
            List<Review> reviews = service.findReviewsByDateRange(searchParameters);
            request.setAttribute(REVIEW_LIST_ATR, reviews);
            System.out.println("searchParameters 2" +searchParameters);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.REVIEW_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindOrderByPrepaymentCommand was failed " + e);
             throw new CommandException("Try to execute FindOrderByPrepaymentCommand was failed ", e);
        }
        return commandResult;
    }
}
