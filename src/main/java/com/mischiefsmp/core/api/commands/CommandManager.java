package com.mischiefsmp.core.api.commands;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.MischiefPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class CommandManager {
    public static void registerCommand(@NotNull MischiefPlugin plugin, @NotNull MischiefCommand command) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(command.getLabel(), command.getCommand());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            plugin.getLogManager().warn("Could not register command %s!", command.getLabel());
            plugin.getLogManager().warn(e.getMessage());
        }
    }

    public static void registerPermissions(Class<?> permissionHolder) {
        Class<?> pClass = Permission.class;
        for(Field field : permissionHolder.getDeclaredFields()) {
            if(field.getType().isAssignableFrom(pClass)) {
                try {
                    Permission p = (Permission) field.get(pClass);
                    Bukkit.getServer().getPluginManager().addPermission(p);
                } catch (IllegalAccessException e) {
                    MischiefCore.getCore().getLogManager().error("Cant register permission for permission handler %s!", permissionHolder);
                }
            }
        }
    }
}
