package com.mischiefsmp.core;

import com.mischiefsmp.core.utils.FileUtils;
import lombok.Getter;

public class MischiefCore extends MischiefPlugin {
    @Getter
    private static LangManager langManager;
    @Getter
    private static PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        FileUtils.init(this);
        pluginConfig = new PluginConfig(this);
        langManager = new LangManager(this, pluginConfig.getLanguages(), pluginConfig.getDefaultLanguage());
    }

    @Override
    public void onDisable() {
    }
}
