package com.mischiefsmp.core;

import com.mischiefsmp.core.config.ConfigFile;
import com.mischiefsmp.core.config.ConfigManager;
import com.mischiefsmp.core.config.ConfigValue;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

//This isn't public, we don't want other Plugins accessing it
class PluginConfig extends ConfigFile {
    @Getter
    @ConfigValue(path = "language")
    private String defaultLanguage;

    @Getter
    @ConfigValue(path = "languages")
    private ArrayList<String> languages;

    public PluginConfig(MischiefPlugin plugin) {
        super(plugin, "config.yml", "config.yml");
        ConfigManager.init(this);
    }
}
