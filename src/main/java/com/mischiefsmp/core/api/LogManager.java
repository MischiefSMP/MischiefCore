package com.mischiefsmp.core.api;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
    private static final HashMap<MischiefPlugin, LogManager> loggers = new HashMap<>();
    private Logger logger;

    public static LogManager getLogManager(MischiefPlugin plugin) {
        if(loggers.containsKey(plugin))
            return loggers.get(plugin);

        LogManager lm = new LogManager();
        lm.logger = plugin.getLogger();
        loggers.put(plugin, lm);
        return lm;
    }

    private void log(Object message, Level level) {
        logger.log(level, message != null ? message.toString() : "null");
    }

    private void logF(Object message, Level level, Object... args) {
        String msg = "null";
        if(message != null)
            msg = args.length == 0 ? message.toString() : String.format(message.toString(), args);
        logger.log(level, msg);
    }

    public void info(Object message) {
        log(message, Level.INFO);
    }

    public void info(Object message, Object... args) {
        logF(message, Level.INFO, args);
    }

    public void warn(Object message) {
        log(message, Level.WARNING);
    }

    public void warn(Object message, Object... args) {
        logF(message, Level.WARNING, args);
    }

    public void error(Object message) {
        log(message, Level.SEVERE);
    }

    public void error(Object message, Object... args) {
        logF(message, Level.SEVERE, args);
    }
}
