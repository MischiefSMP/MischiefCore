package com.mischiefsmp.core.api.utils;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.lang.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class PluginManager {
    private static final HashMap<Class<?>, MischiefPlugin> mischiefPlugins = new HashMap<>();

    public static void init() {
        LogManager lm = MischiefCore.getCore().getLogManager();
        lm.info("Checking for MischiefSMP plugins...");
        for(Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
            if(pl instanceof MischiefPlugin plugin) {
                lm.info("-> %s", plugin.getName());
                mischiefPlugins.put(pl.getClass(), plugin);
            }
        }
        lm.info("Done!");
        lm.info("Want more? Run /mischiefcore plugins");
    }

    public static boolean hasPlugin(Class<?> pluginClass) {
        return getMischiefPlugin(pluginClass) != null;
    }

    public static LogManager getLogManager(Class<?> pluginClass) {
        MischiefPlugin plugin = getMischiefPlugin(pluginClass);
        if(plugin != null)
            return plugin.getLogManager();
        throw pnfe(pluginClass);
    }

    public static LangManager getLangManager(Class <?> pluginClass) {
        MischiefPlugin plugin = getMischiefPlugin(pluginClass);
        if(plugin != null)
            return plugin.getLangManager();
        throw pnfe(pluginClass);
    }

    public static MischiefPlugin getMischiefPlugin(Class<?> pluginClass) {
        if(mischiefPlugins.containsKey(pluginClass))
            return mischiefPlugins.get(pluginClass);
        throw pnfe(pluginClass);
    }

    private static PluginNotFoundException pnfe(Class<?> clazz) {
        return new PluginNotFoundException(String.format("MischiefPlugin %s not found!", clazz));
    }
}
