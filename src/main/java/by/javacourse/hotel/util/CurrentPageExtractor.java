package by.javacourse.hotel.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * {@code CurrentPageExtractor} util class to help extract current page
 */
public class CurrentPageExtractor {

    private static final String CONTROLLER_PART = "/controller?";

    /**
     * {@code extract} method to extract part of current page on forward route from request
     * @param request - type of @{link HttpServletRequest}
     * @return current page
     */
    public static String extract(HttpServletRequest request) {
        String commandPart = request.getQueryString();
        String currentPage = CONTROLLER_PART + commandPart;
        return currentPage;
    }
}
