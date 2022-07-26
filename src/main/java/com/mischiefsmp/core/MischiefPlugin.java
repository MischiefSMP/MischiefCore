package com.mischiefsmp.core;

import com.mischiefsmp.core.lang.LangManager;
import com.mischiefsmp.core.utils.Function;
import com.mischiefsmp.core.utils.LogManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MischiefPlugin extends JavaPlugin {
    @Getter
    private final LogManager logManager;
    @Getter
    private final LangManager langManager;
    private Function reloadFunction;

    public MischiefPlugin() {
        logManager = LogManager.getLogManager(this);
        langManager = new LangManager(this);
        //This is run once the server is fully started
    }

    public void onEnable() {
        onLoad();
        getServer().getScheduler().scheduleSyncDelayedTask(this, this::onFullLoad);
    }

    public void onDisable() {
        onUnload();
    }

    public abstract void onLoad();
    public abstract void onFullLoad();
    public abstract void onUnload();

    public void setReloadFunction(Function function) {
        reloadFunction = function;
    }

    public void reload() {
        logManager.log("Reloading...");
        langManager.reload();
        reloadFunction.run();
        logManager.log("Reload done!");
    }

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
