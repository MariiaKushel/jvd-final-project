package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import by.javacourse.hotel.validator.RoomOrderValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.ORDER_ID_ATR;
import static by.javacourse.hotel.controller.command.RequestAttribute.WRONG_ORDER_ID_ATR;
import static by.javacourse.hotel.controller.command.RequestParameter.ORDER_ID;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class GoToUpdateOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(ORDER_ID_SES, request.getParameter(ORDER_ID));

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomService roomService = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            Optional<RoomOrder> optOrder = roomOrderService.findOrderById(searchParameters);
            if (optOrder.isPresent()) {
                RoomOrder order = optOrder.get();
                String roomId = String.valueOf(order.getRoomId());
                Room room = roomService.findRoomById(roomId).get();
                RoomOrder.Status status = order.getStatus();
                List<RoomOrder.Status> availableStatuses = roomOrderService.findAvailableStatuses(status);
                session.setAttribute(ORDER_SES, order);
                session.setAttribute(ROOM_SES, room);
                session.setAttribute(AVAILABLE_ORDER_STATUSES_SES, availableStatuses);
            } else {
                searchParameters.put(WRONG_ORDER_ID_SES, RoomOrderValidator.WRONG_DATA_MARKER);
            }
            session.removeAttribute(UPDATE_ORDER_RESULT);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.UPDATE_ORDER_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToCancelOrderPageCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }
}
