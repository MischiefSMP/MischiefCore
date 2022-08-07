package com.mischiefsmp.core.api.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandAlias {
    private final MischiefCommand parentCommand;
    @Getter
    private Command executor;

    public CommandAlias(String alias, MischiefCommand parentCommand) {
        this.parentCommand = parentCommand;
        executor = new Command(alias) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return parentCommand.getExecutor().execute(sender, commandLabel, args);
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                return parentCommand.getExecutor().tabComplete(sender, label, args);
            }
        };
    }
}
