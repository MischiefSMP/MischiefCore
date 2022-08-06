package com.mischiefsmp.core;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class PermissionContainer {
    //View help command
    public static Permission CORE_HELP = new Permission("core.help", PermissionDefault.OP);
    //View plugins
    public static Permission CORE_PLUGINS = new Permission("core.plugins", PermissionDefault.FALSE);
    public static Permission CORE_PLUGINS_UNINSTALL = new Permission("core.plugins.uninstall", PermissionDefault.FALSE);
    public static Permission CORE_PLUGINS_INSTALL = new Permission("core.plugins.install", PermissionDefault.FALSE);
}
