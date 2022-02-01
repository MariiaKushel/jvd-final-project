package by.javacourse.hotel.exception;

/**
 * {@code ServiceException} class represent a checked exception from {@link by.javacourse.hotel.model.service}
 * @see Exception
 */
public class ServiceException extends Exception {
    public ServiceException() {

        super();
    }

    public ServiceException(String message) {

        super(message);
    }

    public ServiceException(Exception e) {

        super(e);
    }

    public ServiceException(String message, Exception e) {

        super(message, e);
    }
}
