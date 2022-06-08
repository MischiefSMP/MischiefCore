package com.mischiefsmp.core.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

public class ConfigFile {
    @Getter
    private final Plugin plugin;
    @Getter
    private final String localPath;
    @Getter
    private final String jarPath;

    public ConfigFile(Plugin plugin, String localPath, String jarPath) {
        this.plugin = plugin;
        this.localPath = localPath;
        this.jarPath = jarPath;
    }
}
