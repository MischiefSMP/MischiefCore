package com.mischiefsmp.core.base;

import com.mischiefsmp.core.MischiefCore;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class MischiefCommand {
    private final MischiefCommand instance;

    @Getter
    private final CommandExecutor executor;

    public MischiefCommand(MischiefPlugin plugin) {
        instance = this;
        executor = (sender, command, label, args) -> {
            String message = null;
            switch(instance.onCommand(sender, command, plugin, label, args)) {
                case NO_PERMISSION -> message = "cmd-no-perm";
                case MISSING_ARGUMENTS -> message = "cmd-missing-arg";
                case BAD_ARGUMENTS -> message = "cmd-bad-arg";
                case SERVER_ERROR -> message = "cmd-server-error";
            }

            if(message != null)
                MischiefCore.getLangManager().sendString(sender, message);

            return true;
        };
    }

    public abstract String getAlias();

    public abstract CommandResult onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull MischiefPlugin plugin, @NotNull String label, @NotNull String[] args);
}
