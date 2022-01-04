package by.javacourse.hotel.controller.command.impl;

import by.javacourse.hotel.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
        String currentPage = session.getAttribute(SessionAtribute.CURRENT_PAGE).toString();
        Language newLanguage = Language.valueOf(request.getParameter(RequestParameter.LANGUAGE));
        switch (newLanguage) {
            case EN -> session.setAttribute(SessionAtribute.LOCALE, Language.EN.getLocale());
            case RU -> session.setAttribute(SessionAtribute.LOCALE, Language.RU.getLocale());
        }
        return CommandResult.createRedirectCommandResult(currentPage);
    }
}
