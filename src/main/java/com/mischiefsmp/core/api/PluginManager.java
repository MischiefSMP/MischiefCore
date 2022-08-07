package com.mischiefsmp.core.api;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.lang.LangManager;
import com.mischiefsmp.core.api.utils.PluginInfo;
import com.mischiefsmp.core.api.utils.PluginNotFoundException;
import com.mischiefsmp.core.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PluginManager {
    private static final HashMap<Class<?>, MischiefPlugin> mischiefPlugins = new HashMap<>();
    private static final String PLUGIN_URL = "https://mischiefsmp.com/plugins.json";

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

    public static ArrayList<MischiefPlugin> getMischiefPlugins() {
        ArrayList<MischiefPlugin> arr = new ArrayList<>();
        for(Class<?> key : mischiefPlugins.keySet())
            arr.add(mischiefPlugins.get(key));
        return arr;
    }

    public static ArrayList<PluginInfo> getAvailablePlugins() {
        ArrayList<PluginInfo> list = new ArrayList<>();
        String json = Utils.getJSONFromURL(PLUGIN_URL);
        if(json == null)
            return null;

        JSONArray info = new JSONArray(json);
        for(int i = 0; i < info.length(); i++) {
            JSONObject pl = info.getJSONObject(i);
            list.add(new PluginInfo(pl.getString("name"), pl.getString("version"), pl.getString("github"), pl.getString("download")));
        }
        return list;
    }

    private static PluginNotFoundException pnfe(Class<?> clazz) {
        return new PluginNotFoundException(String.format("MischiefPlugin %s not found!", clazz));
    }
}
