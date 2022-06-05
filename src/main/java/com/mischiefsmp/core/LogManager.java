package com.mischiefsmp.core;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
    private final Logger logger;

    public LogManager(Plugin plugin) {
        logger = plugin.getLogger();
    }

    public void log(Object message, Object... args) {
        log(message, Level.INFO, args);
    }

    public void log(Object message, Level level, Object... args) {
        String msg = "null";
        if(message != null)
            msg = args.length == 0 ? message.toString() : String.format(message.toString(), args);
        logger.log(level, msg);
    }
}
