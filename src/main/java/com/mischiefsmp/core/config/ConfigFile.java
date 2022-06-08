package com.mischiefsmp.core.config;

import org.bukkit.plugin.Plugin;

public class ConfigFile {
    private final Plugin plugin;
    private final String localPath;
    private final String jarPath;

    public ConfigFile(Plugin plugin, String localPath, String jarPath) {
        this.plugin = plugin;
        this.localPath = localPath;
        this.jarPath = jarPath;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getLocalPath() {
        return localPath;
    }

    public String getJarPath() {
        return jarPath;
    }
}
