package com.mischiefsmp.core;

import com.mischiefsmp.core.utils.FileUtils;
import com.mischiefsmp.core.utils.LogManager;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class MischiefCore extends MischiefPlugin {
    private static final HashMap<Class<?>, MischiefPlugin> mischiefPlugins = new HashMap<>();
    private static final HashMap<String, MischiefPlugin> mischiefPluginsByName = new HashMap<>();

    @Override
    public void onLoad() {
        FileUtils.init(this);
    }

    @Override
    public void onFullLoad() {
        LogManager lm = getLogManager();
        lm.log("Indexing Mischief plugins...");
        for(Plugin pl : getServer().getPluginManager().getPlugins()) {
            if(pl instanceof MischiefPlugin plugin) {
                lm.logF("Indexing %s...", plugin.getName());
                mischiefPlugins.put(pl.getClass(), plugin);
                mischiefPluginsByName.put(pl.getName(), plugin);
            }
        }
        lm.log("Done!");
    }

    @Override
    public void onUnload() {

    }

    public static LogManager getLogManager(Class<?> pluginClass) {
        MischiefPlugin plugin = getMischiefPlugin(pluginClass);
        if(plugin != null)
            return plugin.getLogManager();
        return null;
    }

    public static MischiefPlugin getMischiefPlugin(Class<?> pluginClass) {
        if(mischiefPlugins.containsKey(pluginClass))
            return mischiefPlugins.get(pluginClass);
        return null;
    }

    public static MischiefPlugin getMischiefPlugin(String name) {
        if(mischiefPluginsByName.containsKey(name))
            return mischiefPluginsByName.get(name);
        return null;
    }
}
