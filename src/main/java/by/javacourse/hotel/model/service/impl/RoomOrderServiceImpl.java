package by.javacourse.hotel.model.service.impl;

import by.javacourse.hotel.entity.RoomOrder;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DaoProvider;
import by.javacourse.hotel.model.dao.RoomOrderDao;
import by.javacourse.hotel.model.dao.UserDao;
import by.javacourse.hotel.model.service.RoomOrderService;
import by.javacourse.hotel.validator.RoomOrderValidator;
import by.javacourse.hotel.validator.impl.RoomOrderValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static by.javacourse.hotel.controller.command.RequestAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class RoomOrderServiceImpl implements RoomOrderService {
    static Logger logger = LogManager.getLogger();

    private static final String DEFAULT_DATE_FROM = "2020-01-01";

    private DaoProvider provider = DaoProvider.getInstance();
    private UserDao userDao = provider.getUserDao();
    private RoomOrderDao roomOrderDao = provider.getRoomOrderDao();


    @Override
    public boolean createOrder(Map<String, String> orderData) throws ServiceException {
        boolean result = false;
        try {
            long userId = Long.parseLong(orderData.get(USER_ID_SES));
            BigDecimal balance = userDao.findBalanceByUserId(userId);
            RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();
            if (!validator.validateOrderData(orderData, balance)) {
                logger.info("Not enough balance");
                return result;
            }

            long roomId = Long.parseLong(orderData.get(ROOM_ID_SES));
            LocalDate date = LocalDate.parse(orderData.get(DATE_SES));
            LocalDate from = LocalDate.parse(orderData.get(DATE_FROM_SES));
            if(date.compareTo(from) > 0){
                logger.info("Not valid data");
                return result;
            }
            LocalDate to = LocalDate.parse(orderData.get(DATE_TO_SES));
            BigDecimal amount = new BigDecimal(orderData.get(TOTAL_AMOUNT_SES));
            boolean prepayment = orderData.get(PREPAYMENT_SES) != null ? true : false;
            int days = Integer.parseInt(orderData.get(DAYS_SES));

            RoomOrder order = RoomOrder.newBuilder()
                    .setRoomId(roomId)
                    .setUserId(userId)
                    .setDate(date)
                    .setFrom(from)
                    .setTo(to)
                    .setAmount(amount)
                    .setPrepayment(prepayment)
                    .build();

            result = roomOrderDao.createOrderWithRoomStates(order, days);
        } catch (NumberFormatException | DateTimeParseException e) {
            logger.info("Not valid data");
            return result;
        } catch (DaoException e) {
            logger.error("Try to createOrder was failed " + e);
            throw new ServiceException("Try to createOrder  was failed", e);
        }
        return result;
    }

    @Override
    public int countDays(String from, String to) throws ServiceException {
        int days;
        try {
            LocalDate dateFrom = LocalDate.parse(from);
            LocalDate dateTo = LocalDate.parse(to);
            days = (int)ChronoUnit.DAYS.between(dateFrom, dateTo);
            if (days < 1 || days > 180) {
                logger.error("Try to countDays was failed, wrong date range");
                throw new ServiceException("Try to countDays  was failed, wrong date range");
            }
        } catch (DateTimeParseException e) {
            logger.error("Try to countDays was failed " + e);
            throw new ServiceException("Try to countDays  was failed", e);
        }
        return days;
    }

    @Override
    public BigDecimal countBaseAmount(int days, BigDecimal roomPrice) throws ServiceException {
        BigDecimal baseAmount;
        baseAmount = roomPrice.multiply(new BigDecimal(days));
        if (baseAmount.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("Try to countBaseAmount was failed");
            throw new ServiceException("Try to countBaseAmount  was failed");
        }
        return baseAmount;
    }

    @Override
    public BigDecimal countTotalAmount(int days, BigDecimal roomPrice, int discount) throws ServiceException {
        BigDecimal totalAmount = null;
        BigDecimal dis = (new BigDecimal(100).subtract(new BigDecimal(discount))).divide(new BigDecimal(100));
        BigDecimal amount = countBaseAmount(days, roomPrice);
        totalAmount = amount.multiply(dis);
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("Try to countTotalAmount was failed");
            throw new ServiceException("Try to countTotalAmount  was failed");
        }
        return totalAmount;
    }

    @Override
    public List<RoomOrder> findOrderByStatus(Map<String, String> parameters) throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        String tempStatus = parameters.get(ORDER_STATUS_ATR);
        try {
            if (!tempStatus.isEmpty()) {
                RoomOrder.Status status = RoomOrder.Status.valueOf(tempStatus);
                orders = roomOrderDao.findOrderByStatus(status);
            } else {
                orders = roomOrderDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find Order By status was failed " + e);
            throw new ServiceException("Try to find Order By status was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByDateRange(Map<String, String> parameters) throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        String tempFrom = parameters.get(DATE_FROM_ATR);
        String tempTo = parameters.get(DATE_TO_ATR);

        tempFrom = tempFrom.isEmpty()
                ? DEFAULT_DATE_FROM
                : tempFrom;

        tempTo = tempTo.isEmpty()
                ? LocalDate.now().toString()
                : tempTo;
        RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();
        if (!validator.validateDateRange(tempFrom, tempTo)) {
            parameters.put(WRONG_DATE_RANGE_ATR, RoomOrderValidator.WRONG_DATA_MARKER);
            return orders;
        }
        try {
            LocalDate from = LocalDate.parse(tempFrom);
            LocalDate to = LocalDate.parse(tempTo);
            orders = roomOrderDao.findOrderByDateRange(from, to);
        } catch (DaoException e) {
            logger.error("Try to find Order By date range was failed " + e);
            throw new ServiceException("Try to find Order By date range was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByPrepayment(Map<String, String> parameters) throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        String tempPrepayment = parameters.get(PREPAYMENT_ATR);
        try {
            if (!tempPrepayment.isEmpty()) {
                boolean prepayment = Boolean.parseBoolean(tempPrepayment);
                orders = roomOrderDao.findOrderByPrepayment(prepayment);
            } else {
                orders = roomOrderDao.findAll();
            }
        } catch (DaoException e) {
            logger.error("Try to find Order By Prepayment was failed " + e);
            throw new ServiceException("Try to find Order By Prepayment was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findAllOrders() throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        try {
            orders = roomOrderDao.findAll();
        } catch (DaoException e) {
            logger.error("Try to find all orders was failed " + e);
            throw new ServiceException("Try to find all orders was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByUserId(long userId) throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        try {
            orders = roomOrderDao.findOrderByUserId(userId);
        } catch (DaoException e) {
            logger.error("Try to find orders by user id was failed " + e);
            throw new ServiceException("Try to find orders by user id was failed", e);
        }
        return orders;
    }

    @Override
    public List<RoomOrder> findOrderByUserIdLast(long userId, Map<String, String> parameters) throws ServiceException {
        List<RoomOrder> orders = new ArrayList<>();
        String tempLast = parameters.get(LAST_ATR);
        RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();
        if (!validator.validateLast(tempLast)) {
            parameters.put(WRONG_LAST_ATR, RoomOrderValidator.WRONG_DATA_MARKER);
            return orders;
        }
        try {
            if (!tempLast.isEmpty()) {
                int last = Integer.parseInt(tempLast);
                orders = roomOrderDao.findOrderByUserIdLast(userId, last);
            } else {
                orders = roomOrderDao.findOrderByUserId(userId);
            }
        } catch (DaoException e) {
            logger.error("Try to findOrderByUserIdLast was failed " + e);
            throw new ServiceException("Try to findOrderByUserIdLast was failed", e);
        }
        return orders;
    }

    @Override
    public Optional<RoomOrder> findOrderById(String orderId) throws ServiceException {
        Optional<RoomOrder> order = Optional.empty();
        try {
            long orderIdL = Long.parseLong(orderId);
            order = roomOrderDao.findEntityById(orderIdL);
        } catch (NumberFormatException e) {
            logger.info("Not valid order id");
        } catch (DaoException e) {
            logger.error("Try to findOrderById was failed " + e);
            throw new ServiceException("Try to findOrderById was failed", e);
        }
        return order;
    }

    @Override
    public boolean updateStatus(String role, RoomOrder.Status newStatus, RoomOrder order) throws ServiceException {
        boolean result = false;
        RoomOrder.Status oldStatus = order.getStatus();
        if (oldStatus == newStatus) {
            logger.info("Nothing to update");
            result = true;
            return result;
        }
        RoomOrderValidator validator = RoomOrderValidatorImpl.getInstance();
        if (!validator.validateStatus(role, oldStatus, newStatus)) {
            logger.info("Not valid status for update");
            return result;
        }
        try {
            RoomOrder orderWithNewStatus = RoomOrder.newBuilder()
                    .setEntityId(order.getEntityId())
                    .setRoomId(order.getRoomId())
                    .setStatus(newStatus)
                    .setAmount(order.getAmount())
                    .setDate(order.getDate())
                    .setFrom(order.getFrom())
                    .setTo(order.getTo())
                    .setPrepayment(order.isPrepayment())
                    .setUserId(order.getUserId())
                    .build();

            result = roomOrderDao.update(orderWithNewStatus);
        } catch (DaoException e) {
            logger.error("Try to updateStatus was failed " + e);
            throw new ServiceException("Try to updateStatus was failed", e);
        }
        return result;
    }

    @Override
    public Map<Long, Boolean> createСanBeCanceledMap(List<RoomOrder> orders) {
        LocalDate today = LocalDate.now();
        Map<Long, Boolean> canBeCanceledMap = new HashMap<>();
        orders.stream()
                .filter(o -> o.getStatus() == RoomOrder.Status.NEW && o.getFrom().compareTo(today) > 0)
                .forEach(o -> canBeCanceledMap.put(o.getEntityId(), true));
        return canBeCanceledMap;
    }

    @Override
    public Map<Long, Boolean> createСanBeUpdatedMap(List<RoomOrder> orders) {
        Map<Long, Boolean> canBeUpdatedMap = new HashMap<>();
        orders.stream()
                .filter(o -> o.getStatus() == RoomOrder.Status.NEW
                        || o.getStatus() == RoomOrder.Status.CONFIRMED
                        || o.getStatus() == RoomOrder.Status.IN_PROGRESS)
                .forEach(o -> canBeUpdatedMap.put(o.getEntityId(), true));
        return canBeUpdatedMap;
    }

    @Override
    public List<RoomOrder.Status> findAvailableStatuses(RoomOrder.Status status) {
        List<RoomOrder.Status> statuses;
        statuses = switch (status) {
            case NEW -> Arrays.asList(RoomOrder.Status.CONFIRMED, RoomOrder.Status.CANCELED_BY_ADMIN);
            case CONFIRMED -> Arrays.asList(RoomOrder.Status.IN_PROGRESS, RoomOrder.Status.CANCELED_BY_ADMIN);
            case IN_PROGRESS -> Arrays.asList(RoomOrder.Status.COMPLETED);
            default -> new ArrayList<>();
        };
        return statuses;
    }

}

