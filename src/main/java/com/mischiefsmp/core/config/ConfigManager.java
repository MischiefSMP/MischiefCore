package com.mischiefsmp.core.config;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.MischiefPlugin;
import com.mischiefsmp.core.utils.FileUtils;
import com.mischiefsmp.core.utils.TimeUtils;
import com.mischiefsmp.core.utils.Utils;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;

public class ConfigManager {

    public static void init(ConfigFile file) {
        MischiefPlugin pl = file.getPlugin();
        String jarPath = file.getJarPath();
        String localPath = file.getLocalPath();
        if(file.getJarPath() != null) {
            //Only copy if file doesn't exist yet
            if(!new File(pl.getDataFolder(), localPath).exists())
                FileUtils.copyConfig(pl, jarPath, localPath);
        }

        //Load config
        FileConfiguration fc = FileUtils.loadConfig(pl, localPath);

        FileConfiguration freshCFG = FileUtils.loadConfigFromJar(pl, jarPath);
        //Load defaults if missing
        if(freshCFG != null) {
            if(FileUtils.loadDefaults(fc, freshCFG))
                FileUtils.save(fc, pl, jarPath);
        }

        load(file, fc);
    }

    //Reset a config by temporarily copying a fresh config and loading values from it
    public static void reset(ConfigFile file, int... indexes) {
        MischiefPlugin pl = file.getPlugin();
        String fn = String.format("TEMP_CFG_%d.yml", TimeUtils.getUnixTime());
        FileUtils.copyConfig(pl, file.getJarPath(), fn);
        FileConfiguration fc = FileUtils.loadConfig(pl, fn);
        load(file, fc, indexes);
        FileUtils.delete(new File(pl.getDataFolder(), fn));
    }

    public static void save(ConfigFile file) {
        FileConfiguration fc = FileUtils.loadConfig(file.getPlugin(), file.getLocalPath());
        try {
            for(Field field : file.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                ConfigValue annotation = field.getAnnotation(ConfigValue.class);
                if(annotation != null)
                    fc.set(annotation.path(), field.get(file));
            }
            FileUtils.save(fc, file.getPlugin(), file.getLocalPath());
        } catch(Exception exception) {
            file.getPlugin().getLogManager().logF("Error trying to save %s!", file);
            exception.printStackTrace();
        }
    }

    public static void delete(ConfigFile file) {
        FileUtils.delete(new File(file.getPlugin().getDataFolder(), file.getLocalPath()));
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
