package com.mischiefsmp.core.api.commands;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.utils.MCUtils;
import com.mischiefsmp.core.api.utils.Utils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class MischiefCommand{
    private final MischiefCommand instance;
    @Getter
    private final MischiefPlugin plugin;
    private final HashMap<String, MischiefCommand> subCommands = new HashMap<>();

    //If set this permission needs to be allowed on the user to even attempt to run this
    private Permission mainPermission;

    public MischiefCommand(MischiefPlugin plugin, Permission mainPermission) {
        this(plugin);
        this.mainPermission = mainPermission;
    }

    public MischiefCommand(MischiefPlugin plugin) {
        instance = this;
        this.plugin = plugin;
    }

    private boolean hasMainPermission(CommandSender sender) {
        //We use MCUtils.isAllowed here, however, we want it to return true if mainPermission is null.
        if(mainPermission == null)
            return true;
        return MCUtils.isAllowed(sender, mainPermission);
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
    public abstract List<String> onTab(@NotNull CommandSender sender, @NotNull MischiefPlugin plugin, @NotNull String[] args);

    public void addSubCommand(MischiefCommand command) {
        subCommands.put(command.getPlugin().getName(), command);
    }

    public List<MischiefCommand> getSubCommands() {
        ArrayList<MischiefCommand> cmds = new ArrayList<>();
        for(String key : subCommands.keySet())
            cmds.add(subCommands.get(key));
        return cmds;
    }

    //We use this system so that we can get the same command with a different label, for aliases
    public Command getCommand(String label) {
        return new Command(label) {
            private String[] removeFirst(String[] args) {
                int newLength = args.length - 1;
                String[] newArgs = new String[newLength];
                System.arraycopy(args, 1, newArgs, 0, newLength);
                return newArgs;
            }

            private MischiefCommand getSubCommand(String[] args) {
                if(args.length != 0) {
                    for(String key : subCommands.keySet()) {
                        MischiefCommand cmd = subCommands.get(key);
                        if(cmd != null && cmd.isSameLabelAlias(args[0]))
                            return cmd;
                    }
                }
                return null;
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                ArrayList<String> parts = null;

                MischiefCommand cmd = getSubCommand(args);

                //If a subcommand is found we forward the tabComplete to it
                if(cmd != null)
                    parts = new ArrayList<>(this.tabComplete(sender, label, removeFirst(args)));
                if(parts == null && hasMainPermission(sender)) {
                    //If no subcommand is found & we have the permission for this command we run the onTab method
                    //and we add all subcommands (if the sender has the permission to run them)
                    parts = new ArrayList<>(instance.onTab(sender, plugin, args));
                    for(String key : subCommands.keySet()) {
                        MischiefCommand subCmd = subCommands.get(key);
                        if(subCmd.hasMainPermission(sender))
                            parts.add(subCommands.get(key).getLabel());
                    }
                }

                Utils.removeTabArguments(args, parts);
                if(parts == null)
                    parts = new ArrayList<>();
                return parts;
            }

            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                String message = null;

                //Check for sub commands
                MischiefCommand cmd = getSubCommand(args);
                if(cmd != null) {
                    this.execute(sender, label, removeFirst(args));
                    return true;
                }

                if(hasMainPermission(sender)) {
                    switch (instance.onCommand(sender, this, plugin, label, args)) {
                        case NO_PERMISSION -> message = "cmd-no-perm";
                        case MISSING_ARGUMENTS -> message = "cmd-missing-arg";
                        case BAD_ARGUMENTS -> message = "cmd-bad-arg";
                        case SERVER_ERROR -> message = "cmd-server-error";
                    }
                } else {
                    message = "cmd-no-perm";
                }

                if(message != null)
                    MischiefCore.getCore().getLangManager().sendString(sender, message);
                return true;
            }
        };
    }
}
