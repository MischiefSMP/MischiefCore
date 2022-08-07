package com.mischiefsmp.core.api.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public static String getJSONFromURL(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).header("accept", "application/json").build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200 ? response.body() : null;
        } catch(URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
