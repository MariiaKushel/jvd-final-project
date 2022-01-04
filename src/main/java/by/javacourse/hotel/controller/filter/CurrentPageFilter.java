package by.javacourse.hotel.controller.filter;

import by.javacourse.hotel.controller.command.SessionAtribute;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"*.jsp"})
public class CurrentPageFilter implements Filter {

    private static final String PAGE_PATH_REGEX = "jsp.+\\.jsp";
    private String currentPage;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();

        Pattern pattern = Pattern.compile(PAGE_PATH_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            currentPage = matcher.group();
        }

        if (currentPage != null) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionAtribute.CURRENT_PAGE, currentPage);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
