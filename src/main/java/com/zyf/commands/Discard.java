package com.zyf.commands;

import java.util.List;
import java.util.Queue;

public class Discard extends AbstractCommand {
    private final String DISCARD_WITHOUT_MULTI_ERR = "ERR DISCARD without MULTI";
    @Override
    public byte[] execute(List<Object> content) {
        if (!Multi.multiStateOpen()) {
            return buildSimpleErrResponse(DISCARD_WITHOUT_MULTI_ERR);
        }

        Queue<List<Object>> commandQueue = Multi.getCommandQueue();
        commandQueue.clear();
        Multi.switchMulti(false);
        return buildSimpleStrResponse("OK");
    }
}
