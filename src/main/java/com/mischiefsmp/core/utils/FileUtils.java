package com.mischiefsmp.core.utils;

import com.mischiefsmp.core.MischiefCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileUtils {
    public static FileConfiguration loadConfig(Plugin plugin, String name) {
        try {
            File cfgFinalFile = new File(plugin.getDataFolder(), name);
            if(!cfgFinalFile.exists())
                plugin.saveResource(name, false);

            YamlConfiguration cfg = new YamlConfiguration();
            cfg.load(cfgFinalFile);
            return cfg;
        } catch(Exception exception) {
            exception.printStackTrace();
            MischiefCore.getLogManager().logF("Error loading file: %s for plugin %s", name, plugin);
            return null;
        }
    }
}
