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
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

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
            request.setAttribute(NEW_SEARCH_ATR, true);
            session.setAttribute(MIN_PRICE_SES, minPrice);
            session.setAttribute(MAX_PRICE_SES, maxPrice);
            session.setAttribute(ALL_PLACES_SES, allSleepingPlace);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.BOOK_ROOM_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToBookRoomPageCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
