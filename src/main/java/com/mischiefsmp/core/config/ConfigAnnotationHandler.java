package com.mischiefsmp.core.config;

import com.mischiefsmp.core.MischiefCore;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ConfigAnnotationHandler {

    public void save(FileConfiguration config, Object classToSave, File file) {
        try {
            for(Field field : classToSave.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                ConfigValue annotation = field.getAnnotation(ConfigValue.class);
                if(annotation != null)
                    config.set(annotation.path(), field.get(classToSave));
            }
            config.save(file);
        } catch(Exception exception) {
            MischiefCore.getLogManager().logF("Error trying to save %s from class %s for indexes", config, classToSave);
        }
    }

    public void load(FileConfiguration config, Object classToLoad, int... indexes) {
        try {
            Field[] fields = classToLoad.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                ConfigValue annotation = field.getAnnotation(ConfigValue.class);
                if (annotation != null) {
                    boolean doSet = false;

                    if (indexes.length > 0) {
                        for (int nr : indexes) {
                            if (nr == i) {
                                doSet = true;
                                break;
                            }
                        }
                    } else {
                        doSet = true;
                    }

                    if (doSet)
                        field.set(classToLoad, config.get(annotation.path()));
                }
            }
        } catch(Exception exception) {
            MischiefCore.getLogManager().logF("Error trying to load %s from class %s for indexes %s", config, classToLoad, Arrays.toString(indexes));
        }
    }
}
