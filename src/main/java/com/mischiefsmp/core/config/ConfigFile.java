package com.mischiefsmp.core.config;

import com.mischiefsmp.core.MischiefPlugin;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

public class ConfigFile {
    @Getter
    private final MischiefPlugin plugin;
    @Getter
    private final String localPath;
    @Getter
    private final String jarPath;

    public ConfigFile(MischiefPlugin plugin, String localPath, String jarPath) {
        this.plugin = plugin;
        this.localPath = localPath;
        this.jarPath = jarPath;
    }
}
