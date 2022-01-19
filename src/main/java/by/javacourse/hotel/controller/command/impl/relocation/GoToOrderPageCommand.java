package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        UserService userService = provider.getUserService();

        String userId = session.getAttribute(CURRENT_USER_ID).toString();
        int discount;
        try {
            discount = userService.findDiscountByUserId(userId);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToOrderPageCommand was failed" + e);
            return new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }

        String from = request.getParameter(DATE_FROM);
        String to = request.getParameter(DATE_TO);
        String price = request.getParameter(ROOM_PRICE);

        int days = roomOrderService.countDays(from, to);
        BigDecimal baseAmount = roomOrderService.countBaseAmount(days, price);
        BigDecimal totalAmount = roomOrderService.countTotalAmount(days, price, discount);

        request.setAttribute(DATE_FROM_ATR, from);
        request.setAttribute(DATE_TO_ATR, to);
        request.setAttribute(ROOM_ID_ATR, request.getParameter(ROOM_ID));
        request.setAttribute(BASE_AMOUNT_ATR, baseAmount);
        request.setAttribute(TOTAL_AMOUNT_ATR, totalAmount);
        request.setAttribute(DAYS_ATR, days);
        request.setAttribute(ROOM_PRICE_ATR, request.getParameter(ROOM_PRICE));
        request.setAttribute(ROOM_NUMBER_ATR, request.getParameter(ROOM_NUMBER));

        session.removeAttribute(ORDER_RESULT);
        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.ORDER_PAGE, FORWARD);
    }
}
