package by.javacourse.hotel.controller.listener;

import by.javacourse.hotel.controller.command.PagePath;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDate;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;

/**
 * {@code HttpSessionListenerImpl} class implements functional of {@link HttpSessionListener}
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_EN";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(CURRENT_PAGE, PagePath.INDEX_PAGE);
        LocalDate today = LocalDate.now();
        session.setAttribute(TODAY, today.toString());
        session.setAttribute(TOMORROW, today.plusDays(1).toString());
    }
}
