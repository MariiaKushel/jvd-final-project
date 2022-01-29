package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CancelOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomOrder order = (RoomOrder) session.getAttribute(ORDER_SES);
        String role = session.getAttribute(CURRENT_ROLE).toString();
        RoomOrder.Status newStatus = RoomOrder.Status.CANCELED_BY_CLIENT;

        CommandResult commandResult = null;
        try {
            boolean result = roomOrderService.updateStatus(role, newStatus, order);
            session.setAttribute(UPDATE_ORDER_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.CANCEL_ORDER_PAGE);
            commandResult = new CommandResult(PagePath.CANCEL_ORDER_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CancelOrderCommand was failed" + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

}
