package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
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

public class UpdateOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomOrder order = (RoomOrder) session.getAttribute(ORDER_SES);
        String role = session.getAttribute(CURRENT_ROLE).toString();
        RoomOrder.Status newStatus =  RoomOrder.Status.valueOf(request.getParameter(ORDER_STATUS));

        CommandResult commandResult = null;
        try {
            boolean result = roomOrderService.updateStatus(role, newStatus, order);
            session.setAttribute(UPDATE_ORDER_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.CANCEL_ORDER_PAGE);
            commandResult = new CommandResult(PagePath.CANCEL_ORDER_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CancelOrderCommand was failed" + e);
             throw new CommandException("Try to execute CancelOrderCommand was failed", e);
        }
        return commandResult;
    }

}
