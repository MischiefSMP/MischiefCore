package com.mischiefsmp.core.api;

import com.mischiefsmp.core.api.lang.LangManager;
import com.mischiefsmp.core.api.utils.Function;
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

    public void onLoad() {
        init1_onLoad();
    }

    public void onEnable() {
        init2_onEnable();
        getServer().getScheduler().scheduleSyncDelayedTask(this, this::init3_onDone);
    }

    public void onDisable() {
        init4_onDisable();
    }

    public abstract void init1_onLoad();
    public abstract void init2_onEnable();
    public abstract void init3_onDone();
    public abstract void init4_onDisable();


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
