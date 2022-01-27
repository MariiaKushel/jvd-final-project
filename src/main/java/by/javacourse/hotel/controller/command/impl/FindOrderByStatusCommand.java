package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.RequestParameter.ORDER_STATUS;
import static by.javacourse.hotel.controller.command.RequestParameter.PREPAYMENT;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_PAGE;

public class FindOrderByStatusCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> searchParameters = new HashMap<>();
        searchParameters.put(ORDER_STATUS_ATR, request.getParameter(ORDER_STATUS));

        ServiceProvider provider = ServiceProvider.getInstance();
        RoomOrderService service = provider.getRoomOrderService();
        CommandResult commandResult = null;
        try {
            List<RoomOrder> orders = service.findOrderByStatus(searchParameters);
            Map<Long, Boolean> canBeUpdatedMap = service.createСanBeUpdatedMap(orders);
            request.setAttribute(ORDER_LIST_ATR, orders);
            request.setAttribute(CAN_BE_UPDATED_MAP_ATR, canBeUpdatedMap);
            request.setAttribute(SEARCH_PARAMETER_ATR, searchParameters);
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.ORDER_MANAGEMENT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute FindOrderByPrepaymentCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, 500);
        }
        return commandResult;
    }
}
