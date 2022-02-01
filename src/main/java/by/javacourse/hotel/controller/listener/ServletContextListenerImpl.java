package by.javacourse.hotel.controller.listener;

import by.javacourse.hotel.model.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code UserValidatorImpl} class implements functional of {@link ServletContextListener}
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("--- initialize pool in web listener  ---");
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("--- destroy pool in web listener ---");
        ConnectionPool.getInstance().destroyPool();
    }
}
