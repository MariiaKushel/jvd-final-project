package by.javacourse.hotel.controller.command.impl.common;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.PREPAYMENT;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class CreateOrderCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        Map<String, String> orderData = (Map<String, String>)session.getAttribute(ORDER_DATA_SES);
        orderData.put(DATE_SES, session.getAttribute(TODAY).toString());
        orderData.put(PREPAYMENT_SES, request.getParameter(PREPAYMENT));

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();

        CommandResult commandResult = null;
        try {
            boolean result = roomOrderService.createOrder(orderData);
            session.removeAttribute(ORDER_DATA_SES);
            session.setAttribute(ORDER_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.ORDER_PAGE);
            commandResult = new CommandResult(PagePath.ORDER_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute CreateOrderCommand was failed " + e);
             throw new CommandException("Try to execute CreateOrderCommand was failed ", e);
        }
        return commandResult;
    }
}
