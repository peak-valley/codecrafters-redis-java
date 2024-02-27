package com.zyf.rdb;

import java.util.HashMap;
import java.util.Map;

public class RDBCache {
    private static final Map<String, String> cache = new HashMap<>();

    public static void put(String k, String v) {
        cache.put(k, v);
    }

    public static String get(String k) {
        return cache.get(k);
    }
}
