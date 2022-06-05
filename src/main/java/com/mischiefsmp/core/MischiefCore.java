package com.mischiefsmp.core;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class MischiefCore extends JavaPlugin {
    private static final MCUtils mcUtils = new MCUtils();
    private static final HashMap<Plugin, LogManager> loggers = new HashMap<>();

    @Override
    public void onEnable() {
    }

    public static ILogManager getLogManager(Plugin plugin) {
        if(loggers.containsKey(plugin))
            return loggers.get(plugin);

        LogManager lm = new LogManager(plugin);
        loggers.put(plugin, lm);

        return lm;
    }

    public static IMCUtils getMCUtils() {
        return mcUtils;
    }
}
