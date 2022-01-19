package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.model.service.UserService;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;

public class GoToBookRoomPageCommand implements Command {
    static Logger logger = LogManager.getLogger();
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServiceProvider provider = ServiceProvider.getInstance();
        RoomService roomService = provider.getRoomService();
        CommandResult commandResult = null;
        try {
            BigDecimal minPrice = roomService.findMinPrice();
            BigDecimal maxPrice = roomService.findMaxPrice();
            List<Integer> allSleepingPlace = roomService.findAllPossibleSleepingPlace();
            request.setAttribute(MIN_PRICE_ATR, minPrice);
            request.setAttribute(MAX_PRICE_ATR, maxPrice);
            request.setAttribute(ALL_SLEEPING_PLACE_LIST_ATR, allSleepingPlace);
            request.setAttribute(MAKE_CHOICE_ATR, true);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.BOOK_ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToBookRoomPageCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
