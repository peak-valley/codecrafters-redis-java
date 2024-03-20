package com.zyf.collect;

import com.zyf.stream.StreamData;

import java.util.*;

public class RedisRepository {
    private static final Map<String, TreeSet<StreamData>> streamMap = new HashMap<>();

    public static void putStream(String k, TreeSet<StreamData> v) {
        streamMap.put(k, v);
    }

    public static TreeSet<StreamData> getStream(String k) {
        return streamMap.get(k);
    }

    public static boolean existStream(String k) {
        return streamMap.containsKey(k);
    }
}
