package com.zyf.commands;

import com.zyf.collect.ConnectInfo;
import com.zyf.collect.RedisRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Multi extends AbstractCommand {

    private static final Map<ConnectInfo, Boolean> clientMultiOpen = new ConcurrentHashMap<>();

    private static Map<ConnectInfo, Queue<List<Object>>> commandQueueMap = new ConcurrentHashMap<>();

    @Override
    public byte[] execute(List<Object> content) {
        switchMulti(true);
        return buildSimpleStrResponse("OK");
    }

    public static void multiOffer(List<Object> command) {
        ConnectInfo connectInfo = RedisRepository.getConnectInfo();
        Queue<List<Object>> commandQueue = commandQueueMap.getOrDefault(connectInfo, new LinkedList<>());
        commandQueue.offer(command);
        commandQueueMap.put(connectInfo, commandQueue);
}

    public static Queue<List<Object>> getCommandQueue() {
        ConnectInfo connectInfo = RedisRepository.getConnectInfo();
        Queue<List<Object>> queue = commandQueueMap.getOrDefault(connectInfo, new LinkedList<>());
        return queue;
    }

    public static void switchMulti(boolean open) {
        ConnectInfo connectInfo = RedisRepository.getConnectInfo();
        clientMultiOpen.put(connectInfo, open);
    }

    public static boolean multiStateOpen() {
        ConnectInfo curConn = RedisRepository.getConnectInfo();
        return clientMultiOpen.getOrDefault(curConn, false);
    }
}
