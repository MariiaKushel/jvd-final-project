package by.javacourse.hotel.controller.filter;

import by.javacourse.hotel.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static by.javacourse.hotel.controller.command.PagePath.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_ROLE;

@WebFilter(urlPatterns = {"/jsp/*"})
public class PageSecurityFilter implements Filter {
    private Set<String> guestPages;
    private Set<String> clientPages;
    private Set<String> adminPages;
    private Set<String> allPages;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getServletPath();
        boolean isPageExist = allPages.stream().anyMatch(requestURI::contains);

        if (isPageExist) {
            HttpSession session = request.getSession();
            User.Role role = session.getAttribute(CURRENT_ROLE) == null
                    ? User.Role.GUEST
                    : User.Role.valueOf(session.getAttribute(CURRENT_ROLE).toString());

            boolean isAccept =
                    switch (role) {
                        case CLIENT -> clientPages.stream().anyMatch(requestURI::contains);
                        case ADMIN -> adminPages.stream().anyMatch(requestURI::contains);
                        default -> guestPages.stream().anyMatch(requestURI::contains);
                    };
            if (!isAccept) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestPages = Set.of(
                MAIN_PAGE,
                SING_IN_PAGE,
                SHOW_ROOM_PAGE,
                ROOM_PAGE,
                CREATE_NEW_ACCOUNT_PAGE);

        clientPages = Set.of(
                MAIN_PAGE,
                SHOW_ROOM_PAGE,
                ROOM_PAGE,
                ORDER_PAGE,
                HOME_PAGE,
                CONTACT_PAGE,
                BOOK_ROOM_PAGE,
                CHANGE_PASSWORD_PAGE,
                REPLENISH_BALANCE_PAGE,
                CREATE_REVIEW_PAGE,
                CLIENT_ORDERS_PAGE,
                CANCEL_ORDER_PAGE,
                ACCOUNT_CLIENT_PAGE);

        adminPages = Set.of(
                MAIN_PAGE,
                SHOW_ROOM_PAGE,
                ROOM_PAGE,
                ORDER_PAGE,
                HOME_PAGE,
                CONTACT_PAGE,
                BOOK_ROOM_PAGE,
                CHANGE_PASSWORD_PAGE,
                USER_MANAGEMENT_PAGE,
                UPDATE_ROOM_PAGE,
                UPDATE_REVIEW_PAGE,
                UPDATE_ORDER_PAGE,
                UPDATE_DISCOUNT_PAGE,
                ROOM_MANAGEMENT_PAGE,
                REVIEW_MANAGEMENT_PAGE,
                REMOVE_DISCOUNT_PAGE,
                ORDER_MANAGEMENT_PAGE,
                DISCOUNT_MANAGEMENT_PAGE,
                CREATE_REVIEW_PAGE,
                CREATE_DISCOUNT_PAGE,
                ACCOUNT_ADMIN_PAGE);

        allPages = new HashSet<>();
        allPages.addAll(guestPages);
        allPages.addAll(clientPages);
        allPages.addAll(adminPages);
    }

    @Override
    public void destroy() {
    }
}
