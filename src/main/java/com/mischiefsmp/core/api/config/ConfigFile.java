package com.mischiefsmp.core.api.config;

import com.mischiefsmp.core.api.MischiefPlugin;
import lombok.Getter;

import java.io.FileNotFoundException;

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

    public void reload() {
        try {
            ConfigManager.init(this);
        } catch (FileNotFoundException exception) {
            plugin.getLogManager().error("Could not reload config %s for plugin %s", localPath, plugin.getName());
        }
    }
}
