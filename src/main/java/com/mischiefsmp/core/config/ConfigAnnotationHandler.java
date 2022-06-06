package com.mischiefsmp.core.config;

import com.mischiefsmp.core.MischiefCore;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ConfigAnnotationHandler {


    public void load(FileConfiguration config, Object classToLoad, int... indexes) throws IllegalAccessException {
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
