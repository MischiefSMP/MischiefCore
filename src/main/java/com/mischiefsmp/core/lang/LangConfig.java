package com.mischiefsmp.core.lang;

import com.mischiefsmp.core.MischiefPlugin;
import com.mischiefsmp.core.config.ConfigFile;
import com.mischiefsmp.core.config.ConfigManager;
import com.mischiefsmp.core.config.ConfigValue;
import lombok.Getter;

import java.util.ArrayList;

public class LangConfig extends ConfigFile {

    @Getter
    @ConfigValue(path = "language")
    private String defaultLanguage;

    @Getter
    @ConfigValue(path = "languages")
    private ArrayList<String> languages;

    public LangConfig(MischiefPlugin plugin) {
        super(plugin, "lang.yml", "lang.yml");
        ConfigManager.init(this);
    }
}
