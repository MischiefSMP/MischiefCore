package com.mischiefsmp.core.api.commands;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.MischiefPlugin;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class MischiefCommand{
    private final MischiefCommand instance;

    @Getter
    private final Command command;

    private final HashMap<String, MischiefCommand> subCommands = new HashMap<>();

    public MischiefCommand(MischiefPlugin plugin) {
        instance = this;
        command = new Command(getLabel()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                String message = null;

                //Check for sub commands
                if(args.length != 0) {
                    for(String key : subCommands.keySet()) {
                        MischiefCommand cmd = subCommands.get(key);
                        if(cmd != null && cmd.isSameLabelAlias(args[0])) {
                            //We found a sub command! Yay!
                            int newLength = args.length - 1;
                            String[] newArgs = new String[newLength];
                            System.arraycopy(args, 1, newArgs, 0, newLength);
                            cmd.getCommand().execute(sender, commandLabel, newArgs);
                            return true;
                        }
                    }
                }

                switch(instance.onCommand(sender, command, plugin, commandLabel, args)) {
                    case NO_PERMISSION -> message = "cmd-no-perm";
                    case MISSING_ARGUMENTS -> message = "cmd-missing-arg";
                    case BAD_ARGUMENTS -> message = "cmd-bad-arg";
                    case SERVER_ERROR -> message = "cmd-server-error";
                }

                if(message != null)
                    MischiefCore.getCore().getLangManager().sendString(sender, message);
                return true;
            }
        };
    }

    public abstract String getLabel();
    public abstract String[] getAliases();

    public boolean isSameLabelAlias(String label) {
        if(getLabel().equals(label))
            return true;

        if(getAliases() == null)
            return false;

        for(String alias : getAliases())
            if(alias.equals(label))
                return true;
        return false;
    }

    public abstract CommandResult onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull MischiefPlugin plugin, @NotNull String label, @NotNull String[] args);

    public void addSubCommand(MischiefCommand command) {
        subCommands.put(command.getLabel(), command);
    }
}
