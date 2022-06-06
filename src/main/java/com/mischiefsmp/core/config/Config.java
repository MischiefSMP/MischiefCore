package com.mischiefsmp.core.config;

import com.mischiefsmp.core.utils.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Config {
    private final File configFile;
    private final FileConfiguration config;
    private final ConfigAnnotationHandler annotationHandler;

    public Config(Plugin plugin, String file) throws IllegalAccessException {
        configFile = new File(plugin.getDataFolder(), file);
        config = FileUtils.loadConfig(plugin, file);
        annotationHandler = new ConfigAnnotationHandler();
        annotationHandler.load(config, this);
    }

    public void save() {
        annotationHandler.save(config, this, configFile);
    }

    public void reset(int... indexes) {
        annotationHandler.load(config, this, indexes);
    }

    public void delete() {
        FileUtils.delete(configFile);
    }
}
