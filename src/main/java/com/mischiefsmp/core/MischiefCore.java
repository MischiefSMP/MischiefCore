package com.mischiefsmp.core;

import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.PluginManager;
import com.mischiefsmp.core.api.commands.CommandManager;
import com.mischiefsmp.core.commands.CoreCommand;

public class MischiefCore extends MischiefPlugin {
    private static MischiefCore coreInstance;

    @Override
    public void init1_onLoad() {
        coreInstance = this;
    }

    @Override
    public void init2_onEnable() {
        CommandManager.registerPermissions(PermissionContainer.class);
        CommandManager.registerCommand(new CoreCommand(this));
    }

    @Override
    public void init3_onDone() {
        PluginManager.init();
    }

    @Override
    public void init4_onDisable() {

    }

    public static MischiefCore getCore() {
        return coreInstance;
    }
}
