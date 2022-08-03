package com.mischiefsmp.core.api.cmdinfo;

import com.mischiefsmp.core.MischiefCore;
import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.utils.FileUtils;
import com.mischiefsmp.core.api.utils.PluginManager;
import com.mischiefsmp.core.api.utils.TimeUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class CMDInfoManager {
    private FileConfiguration fc;

    public CMDInfoManager(MischiefPlugin plugin) {
        String fn = String.format("cmdinfo_%d.yml", TimeUtils.getUnixTime());
        try {
            FileUtils.copyConfig(plugin, "cmdinfo.yml", fn);
            fc = FileUtils.loadConfig(plugin, fn);
            FileUtils.delete(new File(plugin.getDataFolder(), fn));
        } catch (Exception e) {
            PluginManager.getLogManager(MischiefCore.class).error("Could not load cmfinfo for plugin %s!", plugin.getName());
            e.printStackTrace();
        }
    }

    public String getCMDUsage(String cmdKey) {
        return fc.getString(String.format("commands.%s.usage", cmdKey));
    }

    public String getCMDPerm(String cmdKey) {
        return fc.getString(String.format("commands.%s.permission", cmdKey));
    }

    public String getCMDDefault(String cmdKey) {
        return fc.getString(String.format("commands.%s.default", cmdKey));
    }

    //This returns the usage if no exec is set
    public String getCMDExec(String cmdKey) {
        String path = String.format("commands.%s.exec", cmdKey);
        if(fc.contains(path))
            return fc.getString(path);
        return getCMDUsage(cmdKey);
    }

    public String getCMDSuggestion(String cmdKey) {
        String path = String.format("commands.%s.suggest", cmdKey);
        if(fc.contains(path))
            return fc.getString(path);
        return getCMDUsage(cmdKey);
    }

    public HashMap<String, CMDInfo> getAllCMDs() {
        HashMap<String, CMDInfo> result = new HashMap<>();
        ConfigurationSection mainSection = fc.getConfigurationSection("commands");
        if(mainSection == null)
            return null;

        for(String mainKey : mainSection.getKeys(false)) {
            ConfigurationSection idSection = fc.getConfigurationSection(String.format("commands.%s", mainKey));
            if(idSection != null) {
                for(String idKey : idSection.getKeys(false)) {
                    String path = String.format("%s.%s", mainKey, idKey);
                    String usage = getCMDUsage(path);
                    String permission = getCMDPerm(path);
                    String permissionDefault = getCMDDefault(path);
                    String exec = getCMDExec(path);
                    String suggestion = getCMDSuggestion(path);
                    CMDInfo info = new CMDInfo(path, usage, permission, permissionDefault, exec, suggestion);
                    result.put(path, info);
                }
            }
        }
        return result;
    }

    //Example: getCMDHelp(sender, "perms");
    //Returns a list of CmdInfo which contains id, usage, permission, execution
    public ArrayList<CMDInfo> getCMDHelp(CommandSender sender, String cmdKey) {
        ArrayList<CMDInfo> result = new ArrayList<>();
        ConfigurationSection section = fc.getConfigurationSection(String.format("commands.%s", cmdKey));
        if(section == null)
            return result;

        for(String key : section.getKeys(false)) {
            String path = String.format("%s.%s", cmdKey, key);
            String permission = getCMDPerm(path);
            if(permission != null && sender.hasPermission(permission)) {
                String usage = getCMDUsage(path);
                String permissionDefault = getCMDDefault(path);
                String exec = getCMDExec(path);
                String suggestion = getCMDSuggestion(path);
                result.add(new CMDInfo(key, usage, permission, permissionDefault, exec, suggestion));
            }
        }

        return result;
    }

}
