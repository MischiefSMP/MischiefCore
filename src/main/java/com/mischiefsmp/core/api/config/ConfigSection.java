package com.mischiefsmp.core.api.config;

import com.mischiefsmp.core.api.MischiefPlugin;
import lombok.Getter;

//Used like ConfigFile, but only has an id
public class ConfigSection {
    @Getter
    private final String label;

    public ConfigSection(String label) {
        this.label = label;
    }
}
