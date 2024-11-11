package com.zyf.commands;

import com.zyf.CommandFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

@Slf4j
public class Exec extends AbstractCommand {
    private final String NOT_START_MULTI = "ERR EXEC without MULTI";
    CommandFactory commandFactory = new CommandFactory();

    @Override
    public byte[] execute(List<Object> content) {
        log.info("multi state:{}", Multi.multiStateOpen());
        if (!Multi.multiStateOpen()) {
            return buildSimpleErrResponse(NOT_START_MULTI);
        }

        Queue<List<Object>> commandQueue = Multi.getCommandQueue();
        List<byte[]> resList = new ArrayList<>();
        int len = 0;
        Multi.switchMulti(false);
        while(commandQueue.peek() != null) {
            List<Object> commands = commandQueue.poll();
            byte[] commandRes = commandFactory.execute(String.valueOf(commands.getFirst()), commands);
            len += commandRes.length;
            resList.add(commandRes);
        }

        if (len == 0) {
            return buildArraysResponse(Collections.emptyList());
        }

        return concatenate(resList, len);
    }

    public byte[] concatenate(List<byte[]> bytesList, int totalLen) {
        ByteBuffer buffer = ByteBuffer.allocate(totalLen);
        for (byte[] bytes : bytesList) {
            buffer.put(bytes);
        }
        return buffer.array();
    }
}
