package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;

public class CreateOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> orderData = new HashMap<>();
        orderData.put(ROOM_ID, request.getParameter(ROOM_ID));
        orderData.put(CURRENT_USER_ID, session.getAttribute(CURRENT_USER_ID).toString());
        orderData.put(ORDER_DATE, session.getAttribute(TODAY).toString());
        orderData.put(DATE_FROM, request.getParameter(DATE_FROM));
        orderData.put(DATE_TO, request.getParameter(DATE_TO));
        orderData.put(TOTAL_AMOUNT, request.getParameter(TOTAL_AMOUNT));
        orderData.put(PREPAYMENT, request.getParameter(PREPAYMENT));

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();

        CommandResult commandResult = null;
        try {
            boolean isCreate = roomOrderService.createOrder(orderData);
            request.setAttribute(ORDER_WAS_CREATED, isCreate);
            commandResult = new CommandResult(PagePath.ORDER_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CreateOrderCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }

        return commandResult;
    }
}
