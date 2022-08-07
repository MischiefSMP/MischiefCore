package com.mischiefsmp.core.api.utils;

import com.mischiefsmp.core.api.cmdinfo.CMDInfo;
import com.mischiefsmp.core.api.cmdinfo.CMDInfoManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MCUtils {
    private static final String UUID_API = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String USERNAME_API = "https://api.mojang.com/user/profiles/%s/names";

    public static UUID UUIDFromString(String uuid) {
        if(uuid == null)
            return null;

        uuid = uuid.replace("-", "");
        return new UUID(
                new BigInteger(uuid.substring(0, 16), 16).longValue(),
                new BigInteger(uuid.substring(16), 16).longValue());
    }

    public static ArrayList<KeyValueStorage<String, Long>> getUsernameInfo(UUID uuid) {
        String rawString = Utils.getJSONFromURL(String.format(USERNAME_API, uuid));
        if(rawString != null) {
            ArrayList<KeyValueStorage<String, Long>> info = new ArrayList<>();
            JSONArray jsonData = new JSONArray(rawString);
            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject data = jsonData.getJSONObject(i);
                long changedAt = data.has("changedToAt") ? data.getLong("changedToAt") : -1;
                info.add(new KeyValueStorage<>(data.getString("name"), changedAt));
            }
            return info;
        }
        return null;
    }

    public static UUID getUserUUID(String username) {
        String rawString = Utils.getJSONFromURL(String.format(UUID_API, username));
        if(rawString != null)
            return UUIDFromString(new JSONObject(rawString).getString("id"));
        return null;
    }

    public static void registerPermissions(CMDInfoManager infoManager) {
        HashMap<String, CMDInfo> cmdInfos = infoManager.getAllCMDs();
        for(String cmdKey : cmdInfos.keySet()) {
            CMDInfo cmdInfo = cmdInfos.get(cmdKey);
            Permission p = new Permission(cmdInfo.permission(), PermissionDefault.getByName(cmdInfo.permissionDefault()));
            Bukkit.getServer().getPluginManager().addPermission(p);
        }
    }

    public static boolean isAllowed(@NotNull CommandSender sender, @NotNull Permission permission) {
        if(sender instanceof ConsoleCommandSender)
            return true;

        if(permission.getDefault().getValue(sender.isOp()))
            return true;

        return sender.hasPermission(permission);
    }

    public static boolean isAllowed(@NotNull CommandSender sender, @NotNull String permission) {
        Permission p = Bukkit.getPluginManager().getPermission(permission);
        if(p == null)
            return false;
        return isAllowed(sender, p);
    }
}