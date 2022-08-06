package com.mischiefsmp.core.commands;

import com.mischiefsmp.core.PermissionContainer;
import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.commands.CommandResult;
import com.mischiefsmp.core.api.commands.MischiefCommand;
import com.mischiefsmp.core.api.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CorePluginsCommand extends MischiefCommand {
    public CorePluginsCommand(MischiefPlugin plugin) {
        super(plugin, PermissionContainer.CORE_PLUGINS);
    }

    @Override
    public String getLabel() {
        return "plugins";
    }

    @Override
    public String[] getAliases() {
        return Utils.array("pl");
    }

    @Override
    public CommandResult onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull MischiefPlugin plugin, @NotNull String label, @NotNull String[] args) {
        return CommandResult.SUCCESS;
    }

    @Override
    public List<String> onTab(@NotNull CommandSender sender, @NotNull MischiefPlugin plugin, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
