package com.zyf.cluster;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterInformation {
    private static final Map<String, String> cache = new HashMap<>();

    public static void put(String k, String v) {
        cache.put(k, v);
    }

    public static String get(String k) {
        return cache.get(k);
    }
}
