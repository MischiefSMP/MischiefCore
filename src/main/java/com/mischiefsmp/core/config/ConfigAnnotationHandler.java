package com.mischiefsmp.core.config;

import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;

public class ConfigAnnotationHandler {
    public void load(ConfigurationSection config, Object classToLoad) throws IllegalAccessException {
        Class<?> clazz = classToLoad.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            ConfigValue annotation = field.getAnnotation(ConfigValue.class);
            if (annotation != null)
                field.set(classToLoad, config.get(annotation.path()));
        }
    }
}
