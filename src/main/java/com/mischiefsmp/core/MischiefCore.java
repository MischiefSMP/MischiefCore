package com.mischiefsmp.core;

import com.mischiefsmp.core.lang.LangManager;
import com.mischiefsmp.core.utils.FileUtils;
import com.mischiefsmp.core.utils.LogManager;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class MischiefCore extends MischiefPlugin {
    private static MischiefCore coreInstance;
    private static final HashMap<Class<?>, MischiefPlugin> mischiefPlugins = new HashMap<>();

    @Override
    public void onLoad() {
        coreInstance = this;
        FileUtils.init(this);
    }

    @Override
    public void onFullLoad() {
        LogManager lm = getLogManager();
        lm.log("Checking for MischiefSMP plugins...");
        for(Plugin pl : getServer().getPluginManager().getPlugins()) {
            if(pl instanceof MischiefPlugin plugin) {
                lm.logF("-> %s", plugin.getName());
                mischiefPlugins.put(pl.getClass(), plugin);
            }
        }
        lm.log("Done!");
        lm.log("Want more? Run /mischiefcore plugins");
    }

    @Override
    public void onUnload() {

    }

    public static MischiefCore getCore() {
        return coreInstance;
    }

    public static boolean hasPlugin(Class<?> pluginClass) {
        return getMischiefPlugin(pluginClass) != null;
    }

    public static LogManager getLogManager(Class<?> pluginClass) {
        MischiefPlugin plugin = getMischiefPlugin(pluginClass);
        if(plugin != null)
            return plugin.getLogManager();
        return null;
    }

    public static LangManager getLangManager(Class <?> pluginClass) {
        MischiefPlugin plugin = getMischiefPlugin(pluginClass);
        if(plugin != null)
            return plugin.getLangManager();
        throw new IllegalPluginAccessException(String.format("MischiefPlugin %s not found!", pluginClass));
    }

    public static MischiefPlugin getMischiefPlugin(Class<?> pluginClass) {
        if(mischiefPlugins.containsKey(pluginClass))
            return mischiefPlugins.get(pluginClass);
        throw new IllegalPluginAccessException(String.format("MischiefPlugin %s not found!", pluginClass));
    }
}
