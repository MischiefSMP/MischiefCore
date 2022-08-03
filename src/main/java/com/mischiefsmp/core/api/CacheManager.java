package com.mischiefsmp.core.api;

import com.mischiefsmp.core.api.utils.KeyValueStorage;
import com.mischiefsmp.core.api.utils.TimeUtils;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class CacheManager {
    private static final int DEFAULT_LIFETIME = -1;
    private static final CacheManager globalCacheManager = new CacheManager();
    private static final HashMap<String, CacheManager> managers = new HashMap<>();

    private final HashMap<Object, KeyValueStorage<Long, Object>> data = new HashMap<>();
    private long lifetime;

    public CacheManager() {
        this(DEFAULT_LIFETIME);
    }

    //Local instance with lifetime
    public CacheManager(long lifetime) {
        this.lifetime = lifetime;
    }

    public String getString(Object key) {
        Object obj = get(key);
        return obj != null ? obj.toString() : null;
    }

    public Object get(Object key) {
        KeyValueStorage<Long, Object> item = data.get(key);
        if(item == null)
            return null;

        //Expired
        if(TimeUtils.getUnixTime() > item.key()) {
            remove(key);
            return null;
        }

        return item.value();
    }

    //Use default lifetime
    public void put(Object key, Object value) {
        put(key, value, lifetime);
    }

    //Provide different lifetime
    public void put(Object key, Object value, long lifetime) {
        long dieWhen = lifetime < 0 ? Long.MAX_VALUE : TimeUtils.getUnixTime() + lifetime;
        data.put(key, new KeyValueStorage<>(dieWhen, value));
    }

    public void remove(Object key) {
        data.remove(key);
    }

    public void clear() {
        data.clear();
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public static CacheManager getInstance(Plugin plugin) {
        return getInstance(plugin, DEFAULT_LIFETIME);
    }

    public static CacheManager getInstance(Plugin plugin, long lifetime) {
        return getInstance(String.valueOf(plugin.hashCode()), lifetime);
    }

    public static CacheManager getInstance(String key) {
        return getInstance(key, DEFAULT_LIFETIME);
    }

    public static CacheManager getInstance(String key, long lifetime) {
        if (managers.containsKey(key)) {
            CacheManager toReturn = managers.get(key);
            toReturn.setLifetime(lifetime);
            return toReturn;
        }

        CacheManager newManager = new CacheManager(lifetime);
        managers.put(key, newManager);
        return newManager;
    }

    public static CacheManager getInstance() {
        return new CacheManager();
    }

    public static CacheManager getGlobalInstance() {
        return globalCacheManager;
    }
}
