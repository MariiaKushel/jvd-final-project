package by.javacourse.hotel.controller.command.impl.admin;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.exception.CommandException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.RequestParameter.RATE;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class RemoveDiscountCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Discount discount = (Discount) session.getAttribute(DISCOUNT_SES);

        ServiceProvider provider = ServiceProvider.getInstance();
        DiscountService service = provider.getDiscountService();

        CommandResult commandResult = null;
        try {
            boolean result = service.removeDiscount(discount);
            session.setAttribute(REMOVE_DISCOUNT_RESULT, result);
            session.setAttribute(CURRENT_PAGE, PagePath.REMOVE_DISCOUNT_PAGE);
            commandResult = new CommandResult(PagePath.REMOVE_DISCOUNT_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Try to execute UpdateDiscountCommand was failed" + e);
             throw new CommandException("Try to execute UpdateDiscountCommand was failed", e);
        }
        return commandResult;
    }
}
