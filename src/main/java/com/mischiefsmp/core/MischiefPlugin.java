package com.mischiefsmp.core;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MischiefPlugin extends JavaPlugin {
    @Getter
    private final MischiefPlugin instance;
    @Getter
    private final LogManager logManager;

    public MischiefPlugin() {
        instance = this;
        logManager = LogManager.getLogManager(this);
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
