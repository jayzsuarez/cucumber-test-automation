package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    private static final Map<String, Logger> loggerInstances = new HashMap<>();

    /**
     * Logs a message at DEBUG level.
     */
    public static void debug(Object clazz, String message) {
        getLogger(clazz).debug(message);
    }

    /**
     * Logs a message at DEBUG level to the specified format and arguments.
     */
    public static void debug(Object clazz, String message, Object... params) {
        getLogger(clazz).debug(message, params);
    }

    /**
     * Logs a message at INFO level.
     */
    public static void info(Object clazz, String message) {
        getLogger(clazz).info(message);
    }

    /**
     * Logs a message at INFO level to the specified format and arguments.
     */
    public static void info(Object clazz, String message, Object... params) {
        getLogger(clazz).info(message, params);
    }

    /**
     * Logs a message at WARN level.
     */
    public static void warn(Object clazz, String message) {
        getLogger(clazz).warn(message);
    }

    /**
     * Logs a message at WARN level to the specified format and arguments.
     */
    public static void warn(Object clazz, String message, Object... params) {
        getLogger(clazz).warn(message, params);
    }

    /**
     * Logs a message at TRACE level.
     */
    public static void trace(Object clazz, String message) {
        getLogger(clazz).trace(message);
    }

    /**
     * Logs a message at TRACE level to the specified format and arguments.
     */
    public static void trace(Object clazz, String message, Object... params) {
        getLogger(clazz).trace(message, params);
    }

    /**
     * Logs a message at ERROR level.
     */
    public static void error(Object clazz, String message) {
        getLogger(clazz).error(message);
    }

    /**
     * Logs a message at ERROR level to the specified format and arguments.
     */
    public static void error(Object clazz, String message, Object... params) {
        getLogger(clazz).error(message, params);
    }

    /**
     * Gets a {@link Logger} from class instance.
     */
    public static Logger getLogger(Object obj) {
        if (loggerInstances.containsKey(obj.getClass().getSimpleName())) {
            return loggerInstances.get(obj.getClass().getSimpleName());
        } else {
            Logger log = fromFactory(obj.getClass());
            loggerInstances.put(obj.getClass().getSimpleName(), log);
            return log;
        }
    }

    private static Logger fromFactory(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
