package com.mischiefsmp.core.api.config;

import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.utils.FileUtils;
import com.mischiefsmp.core.api.LogManager;
import com.mischiefsmp.core.api.utils.TimeUtils;
import com.mischiefsmp.core.api.utils.Utils;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ConfigManager {

    public static void init(ConfigFile file) throws FileNotFoundException {
        MischiefPlugin pl = file.getPlugin();
        String jarPath = file.getJarPath();
        String localPath = file.getLocalPath();
        if(file.getJarPath() != null) {
            //Only copy if file doesn't exist yet
            if(!new File(pl.getDataFolder(), localPath).exists()) {
                FileUtils.copyConfig(pl, jarPath, localPath);
            }
        }

        //Load config
        FileConfiguration fc = FileUtils.loadConfig(pl, localPath);

        FileConfiguration freshCFG = FileUtils.loadConfigFromJar(pl, jarPath);
        //Load defaults if missing
        try {
            if(freshCFG != null) {
                if(FileUtils.loadDefaults(fc, freshCFG))
                    FileUtils.save(fc, pl, jarPath);
            }
        } catch (IOException ioException) {
            pl.getLogManager().error("Error saving defaults for %s!", pl);
        }

        load(file, fc);
    }

    //Reset a config by temporarily copying a fresh config and loading values from it
    public static void reset(ConfigFile file, int... indexes) {
        MischiefPlugin pl = file.getPlugin();
        LogManager lm = pl.getLogManager();
        String fn = String.format("TEMP_CFG_%d.yml", TimeUtils.getUnixTime());
        try {
            FileUtils.copyConfig(pl, file.getJarPath(), fn);
        } catch (FileNotFoundException e) {
            lm.error("Error resetting config %s for plugin %s: File could not be found!", file.getLocalPath(), file.getPlugin().getName());
        }
        FileConfiguration fc = FileUtils.loadConfig(pl, fn);
        load(file, fc, indexes);
        try {
            FileUtils.delete(new File(pl.getDataFolder(), fn));
        } catch(IOException ioException) {
            lm.error("Error deleting %s for plugin %s!", fn, pl.getName());
        }
    }

    public static FileConfiguration getFCFromConfigSection(ConfigSection section) throws IllegalAccessException {
        return getFC(section);
    }

    public static FileConfiguration getFCFromConfigFile(ConfigFile file) throws IllegalAccessException {
        return getFC(file);
    }

    private static void saveIntoFC(FileConfiguration fc, ConfigSection section) throws IllegalAccessException {
        FileConfiguration newFc = getFC(section);
        if(section.getLabel() != null && !section.getLabel().isEmpty()) {
            fc.set(section.getLabel(), newFc);
        } else {
            for(String key : newFc.getKeys(true)) {
                fc.set(key, newFc.get(key));
            }
        }
    }

    private static FileConfiguration getFC(Object file) throws IllegalAccessException {
        FileConfiguration fc = new YamlConfiguration();
        for (Field field : file.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            ConfigValue annotation = field.getAnnotation(ConfigValue.class);
            if (annotation != null) {
                Object obj = field.get(file);
                //ConfigFile ArrayList
                if (obj instanceof List<?> l) {
                    if (l.size() > 0 && l.get(0) instanceof ConfigSection) {
                        for (Object cfgObj : l) {
                            ConfigSection cfg = (ConfigSection) cfgObj;
                            saveIntoFC(fc, cfg);
                        }
                    }
                //Everything else
                } else {
                    fc.set(annotation.path(), field.get(file));
                }
            }
        }
        return fc;
    }

    public static void save(ConfigFile file) {
        try {
            FileConfiguration fc = getFCFromConfigFile(file);
            FileUtils.save(fc, file.getPlugin(), file.getLocalPath());
        } catch(Exception exception) {
            file.getPlugin().getLogManager().error("Error trying to save %s!", file);
            exception.printStackTrace();
        }
    }

    public static void delete(ConfigFile file) {
        try {
            FileUtils.delete(new File(file.getPlugin().getDataFolder(), file.getLocalPath()));
        } catch (IOException ioException) {
            file.getPlugin().getLogManager().error("Error deleting file %s for plugin %s!", file.getLocalPath(), file.getPlugin().getName());
        }
    }

    private static void load(ConfigFile file, FileConfiguration fc, int... indexes) {
        try {
            Field[] fields = file.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                ConfigValue annotation = field.getAnnotation(ConfigValue.class);
                if (annotation != null) {
                    if(indexes.length == 0 || Utils.contains(indexes, i)) {
                        String path = annotation.path();
                        if(fc.contains(path)){
                            Object obj = fc.get(path);
                            if(obj instanceof MemorySection ms) {
                                field.set(file, ms.getValues(true));
                            } else {
                                field.set(file, obj);
                            }
                        }
                    }
                }
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
