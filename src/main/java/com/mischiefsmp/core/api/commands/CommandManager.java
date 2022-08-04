package com.mischiefsmp.core.api.commands;

import com.mischiefsmp.core.api.MischiefPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;

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
}
