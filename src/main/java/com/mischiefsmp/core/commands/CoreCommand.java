package com.mischiefsmp.core.commands;

import com.mischiefsmp.core.PermissionContainer;
import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.commands.CommandResult;
import com.mischiefsmp.core.api.commands.MischiefCommand;
import com.mischiefsmp.core.api.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoreCommand extends MischiefCommand {
    public CoreCommand(MischiefPlugin plugin) {
        super(plugin, PermissionContainer.CORE_HELP);
        addSubCommand(new CorePluginsCommand(plugin));
    }

    @Override
    public String getLabel() {
        return "core";
    }

    @Override
    public String[] getAliases() {
        return Utils.array("mischiefcore");
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
