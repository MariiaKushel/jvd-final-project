package by.javacourse.hotel.controller.command.impl.client.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToCancelOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(UPDATE_ORDER_RESULT);

        String orderId = request.getParameter(ORDER_ID);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomService roomService = provider.getRoomService();

        CommandResult commandResult = null;
        try {
            Optional<RoomOrder> optOrder = roomOrderService.findOrderById(orderId);
            if (optOrder.isPresent()) {
                RoomOrder order = optOrder.get();
                String roomId = String.valueOf(order.getRoomId());
                Room room = roomService.findRoomById(roomId).get();
                session.setAttribute(ORDER_SES, order);
                session.setAttribute(ROOM_SES, room);
            } else {
                session.setAttribute(WRONG_ORDER_ID_SES, true);
            }

            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.CANCEL_ORDER_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToCancelOrderPageCommand was failed " + e);
            throw new CommandException("Try to execute GoToCancelOrderPageCommand was failed", e);
        }
        return commandResult;
    }
}
