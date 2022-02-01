package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class FindOrderByDateRangeCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(DATE_FROM_ATR, request.getParameter(DATE_FROM));
        searchParameters.put(DATE_TO_ATR, request.getParameter(DATE_TO));

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService service = provider.getRoomOrderService();
        CommandResult commandResult = null;
        try {
            List<RoomOrder> orders = service.findOrderByDateRange(searchParameters);
            Map<Long, Boolean> canBeUpdatedMap = service.createСanBeUpdatedMap(orders);
            request.setAttribute(ORDER_LIST_ATR, orders);
            request.setAttribute(CAN_BE_UPDATED_MAP_ATR, canBeUpdatedMap);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.ORDER_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindOrderByPrepaymentCommand was failed " + e);
             throw new CommandException("Try to execute FindOrderByPrepaymentCommand was failed ", e);
        }
        return commandResult;
    }
}
