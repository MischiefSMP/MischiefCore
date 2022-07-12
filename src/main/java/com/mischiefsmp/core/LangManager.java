package com.mischiefsmp.core;

import com.mischiefsmp.core.utils.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;

public class LangManager {
    private final String defaultLanguage;
    private final HashMap<String, FileConfiguration> langMaps = new HashMap<>();

    public LangManager(MischiefPlugin plugin, List<String> languages, String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        for(String lang : languages) {
            String file = String.format("lang/%s.yml", lang);
            FileUtils.copyConfig(plugin, file, null);
            FileConfiguration fc = FileUtils.loadConfig(plugin, file);
            FileConfiguration defaults = FileUtils.loadConfigFromJar(plugin, file);
            if(defaults != null) {
                if(FileUtils.loadDefaults(fc, defaults))
                    FileUtils.save(fc, plugin, file);
            }
            langMaps.put(lang, fc);
        }
    }

    public String getString(CommandSender sender, String key, Object... args) {
        //TODO: Add future support for MischiefLanguage (per user language)
        return getString(defaultLanguage, key, args);
    }

    public String getString(String key, Object... args) {
        return getString(defaultLanguage, key, args);
    }

    public String getString(String language, String key, Object... args) {
        String msg = langMaps.get(language).getString(String.format("%s", key));
        if(args.length > 0 && msg != null)
            msg = String.format(msg, args);
        if(msg == null)
            msg = String.format("No String for key <%s>", key);
        return msg;
    }

    public void sendString(CommandSender sender, String key, Object... args) {
        sender.sendMessage(getString(sender, key, args));
    }
}
