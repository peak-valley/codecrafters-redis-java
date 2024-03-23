package com.zyf.collect;

import com.zyf.stream.StreamData;

import java.util.*;

public class RedisRepository {
    private static final Map<String, TreeSet<StreamData>> streamMap = new HashMap<>();
    private static final TreeSet<StreamData> streamId = new TreeSet<>(StreamData::compareTo);

    public static void putStream(String k, TreeSet<StreamData> v) {
        streamMap.put(k, v);
        streamId.addAll(v);
    }

    public static TreeSet<StreamData> getStream(String k) {
        return streamMap.get(k);
    }

    public static boolean existStream(String k) {
        return streamMap.containsKey(k);
    }

    public static StreamData firstStream() {
        try {
            return streamId.first();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public static StreamData lastStream() {
        try {
            return streamId.last();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
