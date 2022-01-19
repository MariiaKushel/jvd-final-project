package by.javacourse.hotel.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.javacourse.hotel.controller.command.SessionAttribute.CURRENT_ROLE;

//FIXME add list command to users
//@WebFilter(urlPatterns = { "/controller" }, servletNames = { "controller" })
public class ControllerSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if(session.getAttribute(CURRENT_ROLE)==null){
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
