package by.javacourse.hotel.controller.filter;

import by.javacourse.hotel.controller.command.SessionAttribute;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//FIXME DELETE IF NOT USE, NOW NOT USE!!!
//@WebFilter(urlPatterns = {"*.jsp"})
//@WebFilter(urlPatterns = {"*.jsp"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
//@WebFilter(dispatcherTypes = {DispatcherType.FORWARD}, urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {

    private static final String PAGE_PATH_REGEX = "jsp.+\\.jsp";
    private String currentPage;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("---filter start---");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        System.out.println("url >>> " + url);
        Pattern pattern = Pattern.compile(PAGE_PATH_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            currentPage = matcher.group();
        }

        if (currentPage != null) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
        }
        System.out.println("---filter end---");
        filterChain.doFilter(servletRequest, servletResponse);
    }
    /*
    private static final String REFERER = "referer";
    private static final String PATH_REGEX = "/controller.+";

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        StringBuffer url = request.getRequestURL();
        String page = url.substring(url.lastIndexOf("/"));
        session.setAttribute("current_page", page);
    }

    private String substringPathWithRegex(String url) {
        Pattern pattern = Pattern.compile(PATH_REGEX);
        String path = null;
        if (url != null) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                path = matcher.group(0);
            } else {
                path = "index.jsp";
            }
        }
        return path;
    }
*/




}
