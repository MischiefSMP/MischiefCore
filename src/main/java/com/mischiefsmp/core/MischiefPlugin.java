package com.mischiefsmp.core;

import com.mischiefsmp.core.commands.MischiefCommand;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MischiefPlugin extends JavaPlugin {
    @Getter
    private static MischiefPlugin instance;
    @Getter
    private static LogManager logManager;

    public MischiefPlugin() {
        instance = this;
        logManager = new LogManager(this);
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(MischiefCommand command) {
        PluginCommand cmd = getCommand(command.getAlias());
        if(cmd != null) {
            cmd.setExecutor(command.getExecutor());
        }
    }
}
