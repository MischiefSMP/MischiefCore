package com.mischiefsmp.core;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MischiefCore extends JavaPlugin {
    @Override
    public void onEnable() {
    }

    public static ILogManager getLogManager(Plugin plugin) {
        return new LogManager(plugin);
    }
}
