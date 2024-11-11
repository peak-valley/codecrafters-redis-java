package com.zyf.commands;

import com.zyf.CommandFactory;
import lombok.extern.slf4j.Slf4j;

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
        List<Object> resList = new ArrayList<>();
        Multi.switchMulti(false);
        while(commandQueue.peek() != null) {
            List<Object> commands = commandQueue.poll();
            byte[] bytes = (byte[]) commands.getFirst();
            final String command = new String(bytes).toUpperCase();
            byte[] commandRes = commandFactory.execute(command, commands);
            resList.add(commandRes);
        }

        if (resList.isEmpty()) {
            return buildArraysResponse(Collections.emptyList());
        }

        return buildCommandArraysResponse(resList);
    }
}
