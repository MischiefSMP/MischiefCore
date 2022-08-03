package com.mischiefsmp.core.api.lang;

import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.utils.FileUtils;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class LangManager {
    private final MischiefPlugin plugin;
    private LangConfig config;
    private final HashMap<String, FileConfiguration> langMaps = new HashMap<>();
    @Getter
    private boolean enabled;

    public LangManager(MischiefPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        //Normally we would run .reload here, but since that can fail and it does not update our
        //"exists" variable we do this instead.
        config = new LangConfig(plugin);
        enabled = config.isExists();
        if(enabled) {
            langMaps.clear();
            load();
        }
    }

    private void load() {
        for(String lang : config.getLanguages()) {
            String file = String.format("lang/%s.yml", lang);
            try {
                FileUtils.copyConfig(plugin, file, null);
            } catch (FileNotFoundException ignored) {
                plugin.getLogManager().logF("Language file %s requested but not found", file);
            }
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
