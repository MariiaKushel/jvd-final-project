package by.javacourse.hotel.exception;

/**
 * {@code DaoException} class represent a checked exception from {@link by.javacourse.hotel.model.dao}
 * @see Exception
 */
public class DaoException extends Exception {
    public DaoException() {

        super();
    }

    public DaoException(String message) {

        super(message);
    }

    public DaoException(Exception e) {

        super(e);
    }

    public DaoException(String message, Exception e) {

        super(message, e);
    }
}
