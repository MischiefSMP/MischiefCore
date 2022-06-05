package com.mischiefsmp.core;

import org.bukkit.plugin.java.JavaPlugin;

public class MischiefCore extends JavaPlugin {
    private static MischiefCore pluginInstance;

    @Override
    public void onEnable() {
        pluginInstance = this;
    }

    public static MischiefCore getInstance() {
        return pluginInstance;
    }
}
