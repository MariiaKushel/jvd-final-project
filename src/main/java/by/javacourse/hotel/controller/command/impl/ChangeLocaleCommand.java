package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.javacourse.hotel.controller.command.CommandResult.SendingType.*;

public class ChangeLocaleCommand implements Command {

    private enum Language {
        EN("en_US"),
        RU("ru_RU");

        private String locale;

        private Language(String locale) {
            this.locale = locale;
        }

        public String getLocale() {
            return locale;
        }
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = session.getAttribute(SessionAttribute.CURRENT_PAGE).toString();
        Language newLanguage = Language.valueOf(request.getParameter(RequestParameter.LANGUAGE));
        switch (newLanguage) {
            case EN -> session.setAttribute(SessionAttribute.LOCALE, Language.EN.getLocale());
            case RU -> session.setAttribute(SessionAttribute.LOCALE, Language.RU.getLocale());
        }
        return new CommandResult(currentPage, FORWARD);
    }
}
