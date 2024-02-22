package com.zyf.cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterInformation {
    private static Map<String, String> cache = new ConcurrentHashMap<>();

    public static void put(String k, String v) {
        cache.put(k, v);
    }

    public static String get(String k) {
        return cache.get(k);
    }
}
