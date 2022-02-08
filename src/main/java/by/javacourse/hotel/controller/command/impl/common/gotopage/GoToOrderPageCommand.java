package by.javacourse.hotel.controller.command.impl.common.gotopage;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.Room;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.*;
import by.javacourse.hotel.validator.RoomOrderValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToOrderPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(ORDER_RESULT);

        String userId = session.getAttribute(CURRENT_USER_ID).toString();
        CommandResult commandResult = null;
        try {
            Map<String, String> orderData = createOrderDataFromRequest(request, userId);
            session.setAttribute(ORDER_DATA_SES, orderData);
            session.setAttribute(CURRENT_PAGE, PagePath.ORDER_PAGE);
            commandResult = new CommandResult(PagePath.ORDER_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToOrderPageCommand was failed" + e);
             throw new CommandException("Try to execute GoToOrderPageCommand was failed", e);
        }
        return commandResult;
    }

    private Map<String, String> createOrderDataFromRequest(HttpServletRequest request, String userId) throws ServiceException {
        String from = request.getParameter(DATE_FROM);
        String to = request.getParameter(DATE_TO);
        String roomId = request.getParameter(ROOM_ID);

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService roomOrderService = provider.getRoomOrderService();
        RoomService roomService = provider.getRoomService();
        DiscountService discountService = provider.getDiscountService();

        Map<String, String> orderData = new HashMap<>();
        Optional<Room> optRoom = roomService.findRoomById(roomId);
        if (!optRoom.isPresent()){
            orderData.put(NOT_FOUND_SES, RoomOrderValidator.WRONG_DATA_MARKER);
            return orderData;
        }
        Room room = optRoom.get();
        BigDecimal price = room.getPricePerDay();
        Discount discount = discountService.findDiscountByUserId(userId).get();
        int rate = discount.getRate();
        int days = roomOrderService.countDays(from, to);
        BigDecimal baseAmount = roomOrderService.countBaseAmount(days, price);
        BigDecimal totalAmount = roomOrderService.countTotalAmount(days, price, rate);

        orderData.put(DATE_FROM_SES, from);
        orderData.put(DATE_TO_SES, to);
        orderData.put(ROOM_ID_SES, roomId);
        orderData.put(ROOM_NUMBER_SES, String.valueOf(room.getNumber()));
        orderData.put(PRICE_SES, price.toString());
        orderData.put(BASE_AMOUNT_SES, baseAmount.toString());
        orderData.put(TOTAL_AMOUNT_SES, totalAmount.toString());
        orderData.put(DAYS_SES, String.valueOf(days));
        orderData.put(RATE_SES, String.valueOf(rate));
        orderData.put(USER_ID_SES, userId);

        return orderData;
    }
}
