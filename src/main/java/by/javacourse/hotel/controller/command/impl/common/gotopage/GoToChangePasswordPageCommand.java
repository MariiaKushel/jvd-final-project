package by.javacourse.hotel.controller.command.impl.common.gotopage;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.util.CurrentPageExtractor;
import com.oracle.wls.shaded.org.apache.regexp.REDebugCompiler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.CommandResult.SendingType.REDIRECT;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToChangePasswordPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CHANGE_PASSWORD_RESULT);

        Map<String, String> userData = new HashMap<>();
        userData.put(USER_ID_SES, session.getAttribute(CURRENT_USER_ID).toString());
        userData.put(EMAIL_SES, session.getAttribute(CURRENT_EMAIL).toString());

        session.setAttribute(USER_DATA_SES, userData);
        session.setAttribute(CURRENT_PAGE, PagePath.CHANGE_PASSWORD_PAGE);
        return new CommandResult(PagePath.CHANGE_PASSWORD_PAGE, REDIRECT);
    }
}
