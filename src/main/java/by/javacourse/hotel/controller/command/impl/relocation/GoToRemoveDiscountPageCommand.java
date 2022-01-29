package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.Discount;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.DiscountService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestParameter.DISCOUNT_ID;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class GoToRemoveDiscountPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(WRONG_DISCOUNT_ID_SES);
        session.removeAttribute(REMOVE_DISCOUNT_RESULT);

        ServiceProvider provider = ServiceProvider.getInstance();
        DiscountService service = provider.getDiscountService();
        String discountId = request.getParameter(DISCOUNT_ID);

        CommandResult commandResult = null;
        try {
            Optional<Discount> optDiscount = service.findDiscountById(discountId);
            if (optDiscount.isPresent()) {
                Discount discount = optDiscount.get();
                session.setAttribute(DISCOUNT_SES, discount);
            } else {
                session.setAttribute(WRONG_DISCOUNT_ID_SES, true);
            }
            session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
            commandResult = new CommandResult(PagePath.REMOVE_DISCOUNT_PAGE, FORWARD);
        } catch (ServiceException e) {
            logger.error("Try to execute GoToRemoveDiscountPageCommand was failed " + e);
            commandResult = new CommandResult(PagePath.ERROR_500_PAGE, ERROR, SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return commandResult;
    }

}
