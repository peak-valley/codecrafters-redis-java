package com.zyf.collect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisRepository {
    private static final Map<String, List<String>> streamMap = new HashMap<>();

    public static void putStream(String k, List<String> v) {
        streamMap.put(k, v);
    }

    public static List<String> getStream(String k) {
        return streamMap.get(k);
    }
}
