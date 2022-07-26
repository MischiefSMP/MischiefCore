package com.mischiefsmp.core.utils;

import com.mischiefsmp.core.MischiefPlugin;

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

    public void log(Object message) {
        log(message, Level.INFO);
    }

    public void log(Object message, Level level) {
        logger.log(level, message != null ? message.toString() : "null");
    }

    public void logF(Object message, Object... args) {
        logF(message, Level.INFO, args);
    }

    public void logF(Object message, Level level, Object... args) {
        String msg = "null";
        if(message != null)
            msg = args.length == 0 ? message.toString() : String.format(message.toString(), args);
        logger.log(level, msg);
    }
}
