package com.mischiefsmp.core;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MischiefCore extends JavaPlugin {
    @Getter
    private static MischiefCore pluginInstance;
    @Getter
    private static LogManager logManager;

    @Override
    public void onEnable() {
        pluginInstance = this;
        logManager = new LogManager(pluginInstance);
    }
}
