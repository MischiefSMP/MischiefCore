package com.mischiefsmp.core;

import com.mischiefsmp.core.lang.LangManager;
import com.mischiefsmp.core.utils.Function;
import com.mischiefsmp.core.utils.LogManager;
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
    }

    public abstract void onEnable();
    public abstract void onDisable();

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
