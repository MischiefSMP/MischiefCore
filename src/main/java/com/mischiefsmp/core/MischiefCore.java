package com.mischiefsmp.core;

import com.mischiefsmp.core.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class MischiefCore extends JavaPlugin {
    private static MischiefCore pluginInstance;
    private static LogManager logManager;

    @Override
    public void onEnable() {
        pluginInstance = this;
        logManager = new LogManager(pluginInstance);
    }

    public static LogManager getLogManager() {
        return logManager;
    }

    public static MischiefCore getInstance() {
        return pluginInstance;
    }
}
