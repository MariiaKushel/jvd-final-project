package by.javacourse.hotel.controller.listener;

import by.javacourse.hotel.controller.command.SessionAttribute;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDate;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_EN";
    private static final String DEFAULT_PAGE = "index.jsp";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.LOCALE,DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, DEFAULT_PAGE);
        LocalDate today = LocalDate.now();
        session.setAttribute(SessionAttribute.TODAY, today.toString());
        session.setAttribute(SessionAttribute.TOMORROW, today.plusDays(1).toString());
    }
}
