package com.mischiefsmp.core.utils;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.MischiefPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class FileUtils {
    private static LogManager coreLogger;

    public static void init(MischiefCore core) {
        coreLogger = core.getLogManager();
    }

    public static void copyConfig(MischiefPlugin plugin, String file, String finalLocation) throws FileNotFoundException{
        File df = plugin.getDataFolder();
        try {
            File cfgFinalFile = new File(df, file);
            if(!cfgFinalFile.exists())
                plugin.saveResource(file, false);

            if(finalLocation != null)
                FileUtils.renameTo(cfgFinalFile, new File(df, finalLocation));
        } catch(Exception exception) {
            throw new FileNotFoundException(String.format("Could not load file %s for plugin %s", file, plugin));
        }
    }

    public static boolean fileExists(Plugin plugin, String file, boolean inJar) {
        if(!inJar)
            return new File(file).exists();

        try {
            InputStream stream = plugin.getResource(file);
            boolean exists = stream != null;
            if(exists)
                stream.close();
            return exists;
        } catch(IOException ex) {
            return false;
        }
    }

    public static FileConfiguration loadConfig(Plugin plugin, String file) {
        YamlConfiguration cfg = new YamlConfiguration();
        try {
            cfg.load(new File(plugin.getDataFolder(), file));
        } catch (IOException | InvalidConfigurationException e) {
            //This is okay. If it doesn't exist we create it, it simply won't have values
        }
        return cfg;
    }

    public static FileConfiguration loadConfigFromJar(Plugin plugin, String jarPath) {
        InputStream input = plugin.getResource(jarPath);
        if(input == null) return null;

        return YamlConfiguration.loadConfiguration(new InputStreamReader(input));
    }

    public static boolean loadDefaults(FileConfiguration fc, FileConfiguration defaults) {
        boolean modified = false;
        for(String key : defaults.getKeys(true)) {
            if(!fc.contains(key)) {
                fc.set(key, defaults.get(key));
                modified = true;
            }
        }
        return modified;
    }

    public static void save(FileConfiguration fc, MischiefPlugin plugin, String file) {
        try {
            fc.save(new File(plugin.getDataFolder(), file));
        } catch (IOException e) {
            coreLogger.logF("Error saving config %s for plugin %s!", Level.SEVERE, file, plugin.getName());
            e.printStackTrace();
        }
    }

    public static void delete(File... files) {
        if(files.length == 0) {
            coreLogger.log("FileUtils.delete() -> Successfully deleted ... uuuh.. nothing?");
            return;
        }

        for(File file : files) {
            if(!file.delete())
                coreLogger.logF("Deleting file <%s> failed!", file.getAbsolutePath());
        }
    }

    public static void mkdir(File... files) {
        for(File file : files) {
            if(!file.mkdir())
                coreLogger.logF("Could not mkdir folder <%s>!", file.getAbsolutePath());
        }
    }

    public static boolean renameTo(File initial, File later) {
        File parentFile = later.getParentFile();
        if(!parentFile.exists())
            mkdir(parentFile);
        if(!initial.renameTo(later)) {
            coreLogger.logF("Renaming file <%s> to <%s> failed!", initial.getAbsolutePath(), later.getAbsolutePath());
            return false;
        }
        return true;
    }
}
