package com.mischiefsmp.core.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PluginConfig extends ConfigFile {
    @Getter
    @ConfigValue(path = "language")
    private String defaultLanguage;

    @Getter
    @ConfigValue(path = "languages")
    private ArrayList<String> languages;

    public PluginConfig(Plugin plugin) {
        super(plugin, "config.yml", "config.yml");
        ConfigManager.init(this);
    }
}
