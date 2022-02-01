package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToCreateNewAccountPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CREATE_NEW_ACCOUNT_RESULT);
        Map<String, String> userData = new HashMap<>();
        session.setAttribute(USER_DATA_SES, userData);
        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, FORWARD);
    }
}
