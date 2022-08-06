package com.mischiefsmp.core;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class PermissionContainer {
    public static Permission CORE_HELP = new Permission("core.help", PermissionDefault.OP);
    public static Permission CORE_PLUGINS = new Permission("core.plugins", PermissionDefault.FALSE);
}
