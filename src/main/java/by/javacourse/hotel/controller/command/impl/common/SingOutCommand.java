package by.javacourse.hotel.controller.command.impl.common;

import by.javacourse.hotel.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;

public class SingOutCommand implements Command {

    static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new CommandResult(PagePath.MAIN_PAGE, REDIRECT);
    }
}
