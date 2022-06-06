package com.mischiefsmp.core.config;

import com.mischiefsmp.core.utils.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Config {
    private File configFile;
    private FileConfiguration config;
    private ConfigAnnotationHandler annotationHandler;

    public Config(Plugin plugin, String file) throws IllegalAccessException {
        configFile = new File(plugin.getDataFolder(), file);
        config = FileUtils.loadConfig(plugin, file);
        annotationHandler = new ConfigAnnotationHandler();
        annotationHandler.load(config, this);
    }

    public void reset(int... indexes) {
        try {
            annotationHandler.load(config, this, indexes);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
