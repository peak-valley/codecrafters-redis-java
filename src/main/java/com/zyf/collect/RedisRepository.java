package com.zyf.collect;

import com.zyf.stream.StreamData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RedisRepository {
    private static final Map<String, TreeSet<StreamData>> streamMap = new HashMap<>();
    private static final TreeSet<StreamData> streamId = new TreeSet<>(StreamData::compareTo);

    private static final ThreadLocal<ConnectInfo> connectCache = new ThreadLocal<>();

    private static final Map<ConnectInfo, Boolean> clientMultiOpen = new ConcurrentHashMap<>();

    private static Map<ConnectInfo, Queue<List<Object>>> commandQueueMap = new ConcurrentHashMap<>();

    public static void putStream(String k, TreeSet<StreamData> v) {
        streamMap.put(k, v);
        streamId.addAll(v);
    }

    public static void multiOffer(List<Object> command) {
        ConnectInfo connectInfo = connectCache.get();
        Queue<List<Object>> commandQueue = commandQueueMap.getOrDefault(connectInfo, new LinkedList<>());
        commandQueue.offer(command);
    }

    public static Queue<List<Object>> getCommandQueue() {
        ConnectInfo connectInfo = connectCache.get();
        return commandQueueMap.getOrDefault(connectInfo, new LinkedList<>());
    }

    public static void switchMulti(boolean open) {
        ConnectInfo connectInfo = connectCache.get();
        clientMultiOpen.put(connectInfo, open);
    }

    public static boolean multiStateOpen() {
        ConnectInfo curConn = connectCache.get();
        return clientMultiOpen.getOrDefault(curConn, false);
    }

    public static ConnectInfo getConnectInfo() {
        return connectCache.get();
    }

    public static void setConnectCache(String ip, int port) {
        connectCache.set(new ConnectInfo(ip, port));
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
