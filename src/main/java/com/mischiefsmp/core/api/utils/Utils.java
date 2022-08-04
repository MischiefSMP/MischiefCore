package com.mischiefsmp.core.api.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String[] array(String... args) {
        return args;
    }

    public static Object[] array(Object... args) {
        return args;
    }

    public static TextComponent getHoverAndCMDText(String text, String hover, String command) {
        TextComponent txt = new TextComponent(text);
        if(hover != null)
            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
        if(command != null)
            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return txt;
    }

    public static boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toLowerCase().startsWith(str2.toLowerCase());
    }

    public static void removeTabArguments(String[] args, ArrayList<String> tabArguments) {
        if(args == null || args.length == 0 || tabArguments == null)
            return;
        tabArguments.removeIf(str -> !Utils.startsWithIgnoreCase(str, args[args.length - 1]));
    }

    public static boolean contains(int[] list, int nr) {
        for(int i : list)
            if (i == nr)
                return true;
        return false;
    }
}
