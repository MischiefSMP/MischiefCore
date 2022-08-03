package com.mischiefsmp.core.api;

import com.mischiefsmp.core.api.lang.LangManager;
import com.mischiefsmp.core.api.utils.Function;
import com.mischiefsmp.core.api.utils.LogManager;
import lombok.Getter;
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
        logManager.info("Reloading...");
        langManager.reload();
        reloadFunction.run();
        logManager.info("Reload done!");
    }

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
