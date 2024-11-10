package com.zyf.commands;

import com.zyf.CommandFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Exec extends AbstractCommand {
    private final String NOT_START_MULTI = "ERR EXEC without MULTI";
    CommandFactory commandFactory = new CommandFactory();

    @Override
    public byte[] execute(List<Object> content) {
        if (!Multi.multiStateOpen()) {
            return buildSimpleErrResponse(NOT_START_MULTI);
        }

        Queue<List<Object>> commandQueue = Multi.getCommandQueue();
        List<byte[]> resList = new ArrayList<>();
        int len = 0;
        while(commandQueue.peek() != null) {
            List<Object> commands = commandQueue.poll();
            byte[] commandRes = commandFactory.execute(String.valueOf(commands.get(0)), commands);
            len += commandRes.length;
            resList.add(commandRes);
        }

        Multi.switchMulti(false);
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
