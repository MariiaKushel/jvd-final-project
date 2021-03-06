package by.javacourse.hotel.controller.command;

import by.javacourse.hotel.controller.command.impl.*;
import by.javacourse.hotel.controller.command.impl.admin.*;
import by.javacourse.hotel.controller.command.impl.admin.gotopage.*;
import by.javacourse.hotel.controller.command.impl.client.*;
import by.javacourse.hotel.controller.command.impl.client.gotopage.*;
import by.javacourse.hotel.controller.command.impl.common.*;
import by.javacourse.hotel.controller.command.impl.common.gotopage.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

/**
 * {@code CommandProvider} class represent the relations between {@link CommandName} and {@link Command}
 * Hold and provide instance of all classes extends {@link Command}
 */
public final class CommandProvider {
    static Logger logger = LogManager.getLogger();

    private static EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    static {
        commands.put(CommandName.CANCEL_ORDER, new CancelOrderCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(CommandName.CHANGE_PASSWORD, new ChangePasswordCommand());
        commands.put(CommandName.CHANGE_PREVIEW, new ChangePreviewCommand());
        commands.put(CommandName.CREATE_DISCOUNT, new CreateDiscountCommand());
        commands.put(CommandName.CREATE_NEW_ACCOUNT, new CreateNewAccountCommand());
        commands.put(CommandName.CREATE_ORDER, new CreateOrderCommand());
        commands.put(CommandName.CREATE_REVIEW, new CreateReviewCommand());
        commands.put(CommandName.CREATE_ROOM, new CreateRoomCommand());
        commands.put(CommandName.FIND_ALL_DISCOUNTS, new FindAllDiscountsCommand());
        commands.put(CommandName.FIND_ALL_ORDERS, new FindAllOrdersCommand());
        commands.put(CommandName.FIND_ALL_REVIEWS, new FindAllReviewsCommand());
        commands.put(CommandName.FIND_ALL_ROOMS, new FindAllRoomsCommand());
        commands.put(CommandName.FIND_ALL_VISIBLE_ROOMS, new FindAllVisibleRoomsCommand());
        commands.put(CommandName.FIND_DISCOUNT_BY_RATE, new FindDiscountByRateCommand());
        commands.put(CommandName.FIND_ORDER_BY_DATE_RANGE, new FindOrderByDateRangeCommand());
        commands.put(CommandName.FIND_ORDER_BY_PREPAYMENT, new FindOrderByPrepaymentCommand());
        commands.put(CommandName.FIND_ORDER_BY_STATUS, new FindOrderByStatusCommand());
        commands.put(CommandName.FIND_ORDER_BY_USER_ID, new FindOrderByUserIdCommand());
        commands.put(CommandName.FIND_ORDER_BY_USER_ID_LAST, new FindOrderByUserIdLastCommand());
        commands.put(CommandName.FIND_REVIEW_BY_DATE_RANGE, new FindReviewByDateRangeCommand());
        commands.put(CommandName.FIND_ROOM_BY_ID, new FindRoomByIdCommand());
        commands.put(CommandName.FIND_ROOM_BY_NUMBER, new FindRoomByNumberCommand());
        commands.put(CommandName.FIND_ROOM_BY_PARAMETER, new FindRoomByParameterCommand());
        commands.put(CommandName.FIND_ROOM_BY_PRICE_RANGE, new FindRoomByPriceRangeCommand());
        commands.put(CommandName.FIND_ROOM_BY_SLEEPING_PLACE, new FindRoomBySleepingPlaceCommand());
        commands.put(CommandName.FIND_ROOM_BY_VISIBLE, new FindRoomByVisibleCommand());
        commands.put(CommandName.FIND_USER_BY_PARAMETER, new FindUserByParameterCommand());
        commands.put(CommandName.GO_TO_ACCOUNT_PAGE, new GoToAccountPageCommand());
        commands.put(CommandName.GO_TO_BOOK_ROOM_PAGE, new GoToBookRoomPageCommand());
        commands.put(CommandName.GO_TO_CANCEL_ORDER_PAGE, new GoToCancelOrderPageCommand());
        commands.put(CommandName.GO_TO_CHANGE_PASSWORD_PAGE, new GoToChangePasswordPageCommand());
        commands.put(CommandName.GO_TO_CLIENT_ORDERS_PAGE, new GoToClientOrdersPageCommand());
        commands.put(CommandName.GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(CommandName.GO_TO_CREATE_DISCOUNT_PAGE, new GoToCreateDiscountPageCommand());
        commands.put(CommandName.GO_TO_CREATE_NEW_ACCOUNT_PAGE, new GoToCreateNewAccountPageCommand());
        commands.put(CommandName.GO_TO_CREATE_REVIEW_PAGE, new GoToCreateReviewPageCommand());
        commands.put(CommandName.GO_TO_CREATE_ROOM_PAGE, new GoToCreateRoomPageCommand());
        commands.put(CommandName.GO_TO_DISCOUNT_MANAGEMENT_PAGE, new GoToDiscountManagementPageCommand());
        commands.put(CommandName.GO_TO_HOME_PAGE, new GoToHomePageCommand());
        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.GO_TO_ORDER_MANAGEMENT_PAGE, new GoToOrderManagementPageCommand());
        commands.put(CommandName.GO_TO_ORDER_PAGE, new GoToOrderPageCommand());
        commands.put(CommandName.GO_TO_REMOVE_DISCOUNT_PAGE, new GoToRemoveDiscountPageCommand());
        commands.put(CommandName.GO_TO_REPLENISH_BALANCE_PAGE, new GoToReplenishBalancePageCommand());
        commands.put(CommandName.GO_TO_REVIEW_MANAGEMENT_PAGE, new GoToReviewManagementPageCommand());
        commands.put(CommandName.GO_TO_ROOM_MANAGEMENT_PAGE, new GoToRoomManagementPageCommand());
        commands.put(CommandName.GO_TO_SING_IN_PAGE, new GoToSingInPageCommand());
        commands.put(CommandName.GO_TO_UPDATE_DISCOUNT_PAGE, new GoToUpdateDiscountPageCommand());
        commands.put(CommandName.GO_TO_UPDATE_ORDER_PAGE, new GoToUpdateOrderPageCommand());
        commands.put(CommandName.GO_TO_UPDATE_REVIEW_PAGE, new GoToUpdateReviewPageCommand());
        commands.put(CommandName.GO_TO_UPDATE_ROOM_PAGE, new GoToUpdateRoomPageCommand());
        commands.put(CommandName.GO_TO_USER_MANAGEMENT_PAGE, new GoToUserManagementPageCommand());
        commands.put(CommandName.REMOVE_DISCOUNT, new RemoveDiscountCommand());
        commands.put(CommandName.REPLENISH_BALANCE, new ReplenishBalanceCommand());
        commands.put(CommandName.SING_IN, new SingInCommand());
        commands.put(CommandName.SING_OUT, new SingOutCommand());
        commands.put(CommandName.UPDATE_DESCRIPTION, new UpdateDescriptionCommand());
        commands.put(CommandName.UPDATE_DISCOUNT, new UpdateDiscountCommand());
        commands.put(CommandName.UPDATE_ORDER, new UpdateOrderCommand());
        commands.put(CommandName.UPDATE_PERSONAL_DATA, new UpdatePersonalDataCommand());
        commands.put(CommandName.UPDATE_REVIEW, new UpdateReviewCommand());
        commands.put(CommandName.UPDATE_ROOM, new UpdateRoomCommand());
        commands.put(CommandName.UPLOAD_IMAGE, new UploadImageCommand());
    }

    /**
     * ${code getCommand} - return {@link}
     * @param commandName - name of command, type {@link CommandName}
     * @return suitable instanse of class, which implements {@link Command},
     * or default command if such command not present
     */
    public static Command getCommand(String commandName) {
        Command currentCommand;
        try {
            logger.debug(commandName.toUpperCase());
            currentCommand = commands.get(CommandName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.error("Command: " + commandName + ", is not present " + e);
            currentCommand = new DefaultCommand();
        }
        return currentCommand;
    }
}
