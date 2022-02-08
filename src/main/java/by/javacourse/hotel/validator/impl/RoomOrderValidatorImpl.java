package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.entity.User;
import by.javacourse.hotel.validator.RoomOrderValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static by.javacourse.hotel.controller.command.SessionAttribute.PREPAYMENT_SES;
import static by.javacourse.hotel.controller.command.SessionAttribute.TOTAL_AMOUNT_SES;
import static by.javacourse.hotel.entity.RoomOrder.Status.*;

/**
 * {@code RoomOrderValidatorImpl} class implements functional of {@link RoomOrderValidator}
 */
public class RoomOrderValidatorImpl implements RoomOrderValidator {

    private static final int DEFAULT_MAX_LAST = 99999;
    private static final RoomOrderValidatorImpl instance = new RoomOrderValidatorImpl();

    private RoomOrderValidatorImpl() {

    }

    public static RoomOrderValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateOrderData(Map<String, String> orderData, BigDecimal balance) {
        boolean isValid = true;
        boolean isPrerayment = Boolean.parseBoolean(orderData.get(PREPAYMENT_SES));
        if (isPrerayment) {
            BigDecimal totalAmount = new BigDecimal(orderData.get(TOTAL_AMOUNT_SES));
            isValid = balance.compareTo(totalAmount) >= 0 ? true : false;
        }
        return isValid;
    }

    @Override
    public boolean validateDateRange(String dateFrom, String dateTo) {
        boolean isValid = false;
        try {
            LocalDate from = LocalDate.parse(dateFrom);
            LocalDate to = LocalDate.parse(dateTo);
            isValid = from.compareTo(to) <= 0;
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateLast(String last) {
        boolean isValid = false;
        if (last.isEmpty()) {
            isValid = true;
            return isValid;
        }
        try {
            int num = Integer.parseInt(last);
            isValid = num > 0 && num <= DEFAULT_MAX_LAST;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateStatus(String role, RoomOrder.Status oldStatus, RoomOrder.Status newStatus) {
        boolean isValid = false;
        try {
            User.Role userRole = User.Role.valueOf(role);
            isValid = switch (userRole) {
                case CLIENT -> oldStatus == NEW && newStatus == CANCELED_BY_CLIENT;
                case ADMIN -> validateStatusForAdmin(oldStatus, newStatus);
                default -> false;
            };
        } catch (IllegalArgumentException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateStatusForAdmin(RoomOrder.Status oldStatus, RoomOrder.Status newStatus) {
        boolean isValid = false;
        isValid = switch (oldStatus) {
            case NEW -> newStatus == CONFIRMED || newStatus == CANCELED_BY_ADMIN;
            case CONFIRMED -> newStatus == IN_PROGRESS || newStatus == CANCELED_BY_ADMIN;
            case IN_PROGRESS -> newStatus == COMPLETED;
            default -> false;
        };
        return isValid;
    }
}
