package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_DISCOUNT;

public class GoToOrderPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String from = request.getParameter(DATE_FROM);
        String to = request.getParameter(DATE_TO);
        String price = request.getParameter(ROOM_PRICE);
        String discount = session.getAttribute(CURRENT_DISCOUNT).toString();

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        int days = roomOrderService.countDays(from, to);
        BigDecimal baseAmount = roomOrderService.countBaseAmount(days, price);
        BigDecimal totalAmount = roomOrderService.countTotalAmount(days, price, discount);

        request.setAttribute(RequestAttribute.TEMP_DATE_FROM, from);
        request.setAttribute(RequestAttribute.TEMP_DATE_TO, to);
        request.setAttribute(RequestAttribute.TEMP_ROOM_ID, request.getParameter(ROOM_ID));
        request.setAttribute(TEMP_BASE_AMOUNT, baseAmount);
        request.setAttribute(TEMP_TOTAL_AMOUNT,totalAmount);
        request.setAttribute(TEMP_DAYS, days);
        request.setAttribute(TEMP_ROOM_PRICE, request.getParameter(ROOM_PRICE));
        request.setAttribute(TEMP_ROOM_NUMBER, request.getParameter(ROOM_NUMBER));

        session.setAttribute(SessionAttribute.CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.ORDER_PAGE, FORWARD);
    }
}
