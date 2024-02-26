package com.zyf.cluster;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClusterInformation {
    private static final Map<String, String> cache = new HashMap<>();
    public static AtomicInteger offset = new AtomicInteger(-1);
    public static void put(String k, String v) {
        cache.put(k, v);
    }

    public static String get(String k) {
        return cache.get(k);
    }

    public static int offset(int n) {
        return offset.addAndGet(n);
    }

    public static int getOffset() {
        return offset.get();
    }
}
