package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.service.RoomService;
import by.javacourse.hotel.model.service.ServiceProvider;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.ERROR;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.NEW_SEARCH_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToOrderManagementPageCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> statuses = Stream.of(RoomOrder.Status.values())
                .map(e -> e.name())
                .toList();
        List<Boolean> prepayments = new ArrayList<>();
        prepayments.add(Boolean.TRUE);
        prepayments.add(Boolean.FALSE);

        request.setAttribute(NEW_SEARCH_ATR, true);
        session.setAttribute(PREPAYMENT_SES, prepayments);
        session.setAttribute(ALL_ORDER_STATUSES_SES, statuses);
        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.ORDER_MANAGEMENT_PAGE, FORWARD);
    }
}
