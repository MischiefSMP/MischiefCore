package com.mischiefsmp.core.utils;

import com.mischiefsmp.core.utils.UsernameInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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

    public static ArrayList<UsernameInfo> getUsernameInfo(UUID uuid) {
        String rawString = getJSONFromURL(String.format(USERNAME_API, uuid));
        if(rawString != null) {
            ArrayList<UsernameInfo> info = new ArrayList<>();
            JSONArray jsonData = new JSONArray(rawString);
            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject data = jsonData.getJSONObject(i);
                long changedAt = data.has("changedToAt") ? data.getLong("changedToAt") : -1;
                info.add(new UsernameInfo(data.getString("name"), changedAt));
            }
            return info;
        }
        return null;
    }

    public static UUID getUserUUID(String username) {
        String rawString = getJSONFromURL(String.format(UUID_API, username));
        if(rawString != null)
            return UUIDFromString(new JSONObject(rawString).getString("id"));
        return null;
    }

    private static String getJSONFromURL(String url) {
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