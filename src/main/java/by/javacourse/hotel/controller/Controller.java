package by.javacourse.hotel.controller;

import java.io.*;
import java.util.Locale;

import by.javacourse.hotel.controller.command.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = "/controller")
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
        CommandResult result = command.execute(request);

        String toPage = result.getPage();
        CommandResult.SendingType sendingType = result.getSendingType();
/*        toPage = PagePath.ERROR_500_PAGE;
        sendingType = CommandResult.SendingType.ERROR;*/

        switch (sendingType) {
            case FORWARD -> request.getRequestDispatcher(toPage).forward(request, response);
            case REDIRECT -> response.sendRedirect(toPage);
            default -> {
                int errorCode = result.getErrorCode();
                if (errorCode != 0) {
                    response.sendError(errorCode);
                } else {
                    response.sendError(SC_INTERNAL_SERVER_ERROR); //FIXME can do this?
                }
            }
        }
    }

    public void destroy() {
    }
}