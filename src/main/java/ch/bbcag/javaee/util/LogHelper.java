package ch.bbcag.javaee.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {

    private Logger logger;

    public LogHelper(String name) {
        logger = Logger.getLogger(name);
    }

    public void loge(String message) {
        logger.log(Level.SEVERE, message);
    }

    public void loge(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    public void logw(String message) {
        logger.log(Level.WARNING, message);
    }

    public void logw(String message, Throwable t) {
        logger.log(Level.WARNING, message, t);
    }

    public void logi(String message) {
        logger.log(Level.INFO, message);
    }

    public void logi(String message, Throwable t) {
        logger.log(Level.INFO, message, t);
    }

    public void logc(String message) {
        logger.log(Level.CONFIG, message);
    }

    public void logc(String message, Throwable t) {
        logger.log(Level.CONFIG, message, t);
    }

    public void logFine(String message) {
        logger.log(Level.FINE, message);
    }

    public void logFine(String message, Throwable t) {
        logger.log(Level.FINE, message, t);
    }

    public void logFiner(String message) {
        logger.log(Level.FINER, message);
    }

    public void logFiner(String message, Throwable t) {
        logger.log(Level.FINER, message, t);
    }

    public void logFinest(String message) {
        logger.log(Level.FINEST, message);
    }

    public void logFinest(String message, Throwable t) {
        logger.log(Level.FINEST, message, t);
    }

}
