package by.javacourse.hotel.util;

import jakarta.servlet.http.HttpServletRequest;

public class CurrentPageExtractor {
    private static final String CONTROLLER_PART = "/controller?";

    public static String extract(HttpServletRequest request) {

        String commandPart = request.getQueryString();
        System.out.println("getQueryString>> " + commandPart);

        String currentPage = CONTROLLER_PART + commandPart;
        System.out.println("currentPage>> " + currentPage);

        return currentPage;
    }
}
