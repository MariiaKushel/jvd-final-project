package by.javacourse.hotel.controller.filter;

import by.javacourse.hotel.controller.command.CommandName;
import by.javacourse.hotel.controller.command.RequestParameter;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.model.dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import static by.javacourse.hotel.controller.command.CommandName.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_ROLE;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"controller"})
public class ControllerSecurityFilter implements Filter {

    private Set<String> guestCommands;
    private Set<String> clientCommands;
    private Set<String> adminCommands;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String commandName = request.getParameter(RequestParameter.COMMAND);

        try {
            CommandName command = CommandName.valueOf(commandName.toUpperCase());
            User.Role role = session.getAttribute(CURRENT_ROLE) == null
                    ? User.Role.GUEST
                    : User.Role.valueOf(session.getAttribute(CURRENT_ROLE).toString());

            EnumSet<User.Role> roles = command.getAcceptableRole();
            if (roles.contains(role)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
