package com.mischiefsmp.core.api.commands;

import com.mischiefsmp.core.api.MischiefPlugin;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CommandManager {
    public static void registerCommand(@NotNull MischiefPlugin plugin, @NotNull MischiefCommand command) {
        PluginCommand cmd = plugin.getCommand(command.getAlias());
        if(cmd == null) {
            plugin.getLogManager().logF("Could not register command %s", Level.SEVERE, command.getAlias());
            return;
        }
        cmd.setExecutor(command.getExecutor());
    }
}
