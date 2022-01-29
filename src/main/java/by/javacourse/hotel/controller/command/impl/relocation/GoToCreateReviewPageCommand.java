package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.ReviewService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import by.javacourse.hotel.validator.RoomOrderValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.ORDER_ID;
import static by.javacourse.hotel.controller.command.RequestParameter.ROOM_ID;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class GoToCreateReviewPageCommand implements Command {
    static Logger logger = LogManager.getLogger();
    private static final List<Integer> marks = Arrays.asList(1, 2, 3, 4, 5);

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        Map<String, String> reviewData = new HashMap<>();
        reviewData.put(ORDER_ID_SES, request.getParameter(ORDER_ID));
        reviewData.put(USER_ID_SES, session.getAttribute(CURRENT_USER_ID).toString());
        reviewData.put(DATE_SES, session.getAttribute(TODAY).toString());

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomService roomService = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            Optional<RoomOrder> optOrder = roomOrderService.findOrderById(reviewData);
            if (optOrder.isPresent()) {
                RoomOrder order = optOrder.get();
                String roomId = String.valueOf(order.getRoomId());
                Room room = roomService.findRoomById(roomId).get();
                session.setAttribute(ORDER_SES, order);
                session.setAttribute(ROOM_SES, room);
            } else {
                reviewData.put(WRONG_ORDER_ID_SES, RoomOrderValidator.WRONG_DATA_MARKER);
            }
            session.removeAttribute(CREATE_REVIEW_RESULT);
            session.setAttribute(MARKS_SES, marks);
            session.setAttribute(REVIEW_DATA_SES, reviewData);
            session.setAttribute(CURRENT_PAGE, PagePath.CREATE_REVIEW_PAGE);
            commandResult = new CommandResult(PagePath.CREATE_REVIEW_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToCreateReviewPageCommand was failed " + e);
             throw new CommandException("Try to execute GoToCreateReviewPageCommand was failed", e);
        }
        return commandResult;
    }
}
