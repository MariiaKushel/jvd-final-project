package by.javacourse.hotel.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();

    private static final String DB_PROPERTY = "properties.db";
    private static final String DB_DRIVER_KEY = "driver";
    private static final String DB_URL_KEY = "url";
    private static final String DB_USER_KEY = "user";
    private static final String DB_PASSWORD_KEY = "password";
    private static final String DB_DRIVER;
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    private static final int DEFAULT_POOL_SIZE = 8;

    private static ConnectionPool instance;
    private static AtomicBoolean instanceIsExist = new AtomicBoolean(false);
    private static Lock instanceLocker = new ReentrantLock();

    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<ProxyConnection>();
    private BlockingQueue<ProxyConnection> givenAwayConnections = new LinkedBlockingQueue<ProxyConnection>();

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY);
            DB_URL = resourceBundle.getString(DB_URL_KEY);
            DB_USER = resourceBundle.getString(DB_USER_KEY);
            DB_PASSWORD = resourceBundle.getString(DB_PASSWORD_KEY);
            DB_DRIVER = resourceBundle.getString(DB_DRIVER_KEY);
            Class.forName(DB_DRIVER);
        } catch (MissingResourceException e) {
            logger.fatal("Property file not found or has incorrect data " + DB_PROPERTY);
            throw new RuntimeException("Property file not found or has incorrect data " + DB_PROPERTY, e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver not found " + DB_PROPERTY);
            throw new RuntimeException("Driver not found " + DB_PROPERTY, e);
        }
    }

    private ConnectionPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.error("Connection was not creaded " + i);
            }
        }

        if (freeConnections.isEmpty()) {
            logger.fatal("Impossible create connection pool");
            throw new RuntimeException("Impossible create connection pool");
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceIsExist.get()) {
            instanceLocker.lock();
            try {
                if (instanceIsExist.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
            } finally {
                instanceLocker.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = freeConnections.take();
            givenAwayConnections.put(proxyConnection);
        } catch (InterruptedException e) {
            logger.error("Try to get connection from pool was failed " + e);
            Thread.currentThread().interrupt();
        }
        return proxyConnection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxyConnection) {
            try {
                givenAwayConnections.remove(proxyConnection);
                freeConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.error("Try to release connection to pool was failed " + e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.fatal("Unknown connection");
            throw new RuntimeException("Unknown connection");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Try to destroy connection into pool was failed, InterruptedException " + e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Try to destroy connection into pool was failed, SQLException");
            }
        }
        deregisterDriver();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(d -> {
            try {
                DriverManager.deregisterDriver(d);
            } catch (SQLException e) {
                logger.error("Try to deregister driver " + d + " was failed");
            }
        });
    }
}
