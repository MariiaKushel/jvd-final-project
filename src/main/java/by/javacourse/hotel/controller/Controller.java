package by.javacourse.hotel.controller;

import java.io.*;
import java.util.Locale;

import by.javacourse.hotel.controller.command.*;
import by.javacourse.hotel.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = "/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024,
        maxRequestSize = 1024 * 1024)
public class Controller extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandProvider.getCommand(commandName);
        try {
            CommandResult result = command.execute(request);

            String toPage = result.getPage();
            CommandResult.SendingType sendingType = result.getSendingType();

            switch (sendingType) {
                case FORWARD -> request.getRequestDispatcher(toPage).forward(request, response);
                case REDIRECT -> response.sendRedirect(toPage);
                default -> response.sendError(SC_INTERNAL_SERVER_ERROR);
            }
        } catch (CommandException e) {
            response.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void destroy() {
    }
}