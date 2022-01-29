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

public class GoToCreateDiscountPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CREATE_DISCOUNT_RESULT);
        Map<String, String> discountData = new HashMap<>();
        session.setAttribute(DISCOUNT_DATA_SES, discountData);
        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.CREATE_DISCOUNT_PAGE, FORWARD);
    }
}
