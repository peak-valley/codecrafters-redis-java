package com.zyf.collect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleKVCache {
    public static Map<String, KVString> kvStore = new ConcurrentHashMap<>();

    public static KVString get(String key) {
        return kvStore.get(key);
    }

    public static KVString remove(String key) {
        return kvStore.remove(key);
    }

    public static void put(String k, KVString v) {
        kvStore.put(k, v);
    }
}
