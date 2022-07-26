package com.mischiefsmp.core.lang;

import com.mischiefsmp.core.MischiefPlugin;
import com.mischiefsmp.core.utils.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class LangManager {
    private final MischiefPlugin plugin;
    private final LangConfig config;
    private final HashMap<String, FileConfiguration> langMaps = new HashMap<>();

    public LangManager(MischiefPlugin plugin) {
        this.plugin = plugin;
        config = new LangConfig(plugin);
        load();
    }

    public void reload() {
        config.reload();
        langMaps.clear();
        load();
    }

    private void load() {
        for(String lang : config.getLanguages()) {
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
        return getString(config.getDefaultLanguage(), key, args);
    }

    public String getString(String key, Object... args) {
        return getString(config.getDefaultLanguage(), key, args);
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
