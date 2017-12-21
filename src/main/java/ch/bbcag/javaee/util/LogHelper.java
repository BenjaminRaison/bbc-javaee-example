package ch.bbcag.javaee.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {

    private Logger logger;

    public LogHelper(String name) {
        logger = Logger.getLogger(name);
    }

    /**
     * Logs an error
     *
     * @param message the message
     */
    public void loge(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Logs an error
     * @param message the message
     * @param t the error cause
     */
    public void loge(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    /**
     * Logs a warning
     * @param message the message
     */
    public void logw(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Logs a warning
     * @param message the message
     * @param t the warning cause
     */
    public void logw(String message, Throwable t) {
        logger.log(Level.WARNING, message, t);
    }

    /**
     * Logs with level "info"
     * @param message the message
     */
    public void logi(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Logs with level "info"
     * @param message the message
     * @param t the cause
     */
    public void logi(String message, Throwable t) {
        logger.log(Level.INFO, message, t);
    }

    /**
     * Logs with level "config"
     * @param message the message
     */
    public void logc(String message) {
        logger.log(Level.CONFIG, message);
    }

    /**
     * Logs with level "config"
     * @param message the message
     * @param t the cause
     */
    public void logc(String message, Throwable t) {
        logger.log(Level.CONFIG, message, t);
    }

    /**
     * Logs with level "fine"
     * @param message the message
     */
    public void logFine(String message) {
        logger.log(Level.FINE, message);
    }

    /**
     * Logs with level "fine"
     * @param message the message
     * @param t the cause
     */
    public void logFine(String message, Throwable t) {
        logger.log(Level.FINE, message, t);
    }

    /**
     * Logs with level "finer"
     * @param message the message
     */
    public void logFiner(String message) {
        logger.log(Level.FINER, message);
    }

    /**
     * Logs with level "finer"
     * @param message the message
     * @param t the cause
     */
    public void logFiner(String message, Throwable t) {
        logger.log(Level.FINER, message, t);
    }


    /**
     * Logs with level "finest"
     * @param message the message
     */
    public void logFinest(String message) {
        logger.log(Level.FINEST, message);
    }

    /**
     * Logs with level "finest"
     * @param message the message
     * @param t the cause
     */
    public void logFinest(String message, Throwable t) {
        logger.log(Level.FINEST, message, t);
    }

}
