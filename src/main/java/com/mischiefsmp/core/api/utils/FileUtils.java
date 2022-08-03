package com.mischiefsmp.core.api.utils;

import com.mischiefsmp.core.api.MischiefPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class FileUtils {
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

    public static void save(FileConfiguration fc, MischiefPlugin plugin, String file) throws IOException {
        fc.save(new File(plugin.getDataFolder(), file));
    }

    public static void delete(File... files) throws IOException {
        for(File file : files) {
            if(!file.delete())
                throw new IOException(String.format("Deleting file <%s> failed!", file.getAbsolutePath()));
        }
    }

    public static void mkdir(File... files) throws IOException {
        for(File file : files) {
            if(!file.mkdir())
                throw new IOException(String.format("Could not mkdir folder <%s>!", file.getAbsolutePath()));
        }
    }

    public static void renameTo(File initial, File later) throws IOException {
        File parentFile = later.getParentFile();
        if(!parentFile.exists())
            mkdir(parentFile);
        if(!initial.renameTo(later)) {
            throw new IOException(String.format("Renaming file <%s> to <%s> failed!", initial.getAbsolutePath(), later.getAbsolutePath()));
        }
    }
}
