package by.javacourse.hotel.controller;

import java.io.*;
import java.util.Locale;

import by.javacourse.hotel.controller.command.Command;
import by.javacourse.hotel.controller.command.CommandProvider;
import by.javacourse.hotel.controller.command.CommandResult;
import by.javacourse.hotel.controller.command.RequestParameter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandProvider.getCommand(commandName);
        CommandResult result = command.execute(request);

        String toPage = result.getPage();
        CommandResult.SendingType sendingType = result.getSendingType();
        switch (sendingType){
            case FORWARD:
                request.getRequestDispatcher(toPage).forward(request,response);
                break;
            case REDIRECT:
                response.sendRedirect(toPage);
                break;
            case ERROR:
                response.sendRedirect(toPage); //TODO change to sendError by code
                break;
        }
    }
    public void destroy() {
    }
}