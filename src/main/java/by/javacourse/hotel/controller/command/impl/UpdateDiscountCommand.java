package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.ORDER_STATUS;
import static by.javacourse.hotel.controller.command.RequestParameter.RATE;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class UpdateDiscountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> discountData = (Map<String, String>) session.getAttribute(DISCOUNT_DATA_SES);

        cleanWrongMessage(discountData);
        updateDiscountDataFromRequest(discountData, request);

        ServiceProvider provider = ServiceProvider.getInstance();
        DiscountService service = provider.getDiscountService();

        CommandResult commandResult = null;
        try {
            int sizeBefore = discountData.size();
            boolean result = service.updateDiscount(discountData);
            int sizeAfter = discountData.size();
            if (sizeBefore == sizeAfter) {
                session.removeAttribute(DISCOUNT_DATA_SES);
                session.setAttribute(UPDATE_DISCOUNT_RESULT, result);
            } else {
                session.setAttribute(DISCOUNT_DATA_SES, discountData);
            }
            session.setAttribute(CURRENT_PAGE, PagePath.UPDATE_DISCOUNT_PAGE);
            commandResult = new CommandResult(PagePath.UPDATE_DISCOUNT_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateDiscountCommand was failed" + e);
             throw new CommandException("Try to execute UpdateDiscountCommand was failed", e);
        }
        return commandResult;
    }

    private void updateDiscountDataFromRequest(Map<String, String> discountData, HttpServletRequest request) {
        discountData.put(RATE_SES, request.getParameter(RATE));
    }

    private void cleanWrongMessage(Map<String, String> discountData) {
        discountData.remove(WRONG_RATE_SES);
    }
}
