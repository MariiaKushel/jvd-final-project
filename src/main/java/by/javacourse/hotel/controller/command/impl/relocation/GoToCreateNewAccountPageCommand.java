package by.javacourse.hotel.controller.command.impl.relocation;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.PagePath;
import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.FORWARD;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class GoToCreateNewAccountPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(EMAIL_SES);
        session.removeAttribute(NAME_SES);
        session.removeAttribute(PHONE_NUMBER_SES);
        session.removeAttribute(PASSWORD_SES);
        session.removeAttribute(REPEAT_PASSWORD_SES);

        session.removeAttribute(WRONG_EMAIL_SES);
        session.removeAttribute(WRONG_EMAIL_EXIST_SES);
        session.removeAttribute(WRONG_NAME_SES);
        session.removeAttribute(WRONG_PHONE_NUMBER_SES);
        session.removeAttribute(WRONG_REPEAT_PASSWORD_SES);
        session.removeAttribute(WRONG_PASSWORD_SES);

        session.setAttribute(CURRENT_PAGE, CurrentPageExtractor.extract(request));
        return new CommandResult(PagePath.CREATE_NEW_ACCOUNT_PAGE, FORWARD);
    }
}
