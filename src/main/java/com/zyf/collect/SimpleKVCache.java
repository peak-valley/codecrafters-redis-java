package com.zyf.collect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleKVCache {
    public static final Map<byte[], KVString> kvStore = new HashMap<>();

    public static KVString get(byte[] key) {
        return kvStore.get(key);
    }

    public static KVString remove(byte[] key) {
        return kvStore.remove(key);
    }

    public static void put(byte[] k, KVString v) {
        kvStore.put(k, v);
    }
}
