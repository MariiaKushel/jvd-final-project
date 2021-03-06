package by.javacourse.hotel.controller.command.impl.client.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.RequestAttribute.NEW_SEARCH_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToClientOrdersPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        request.setAttribute(NEW_SEARCH_ATR, true);
        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.CLIENT_ORDERS_PAGE, FORWARD);
    }
}
