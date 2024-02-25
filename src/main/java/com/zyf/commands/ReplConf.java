package com.zyf.commands;

import java.util.List;

public class ReplConf extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("REPLCONF is running");
        if (content.size() < 2) {
            return buildSimpleStrResponse("OK");
        }
        String param1 = new String((byte[]) content.get(1));
        if ("GETACK".equals(param1)) {
            return buildArraysResponse(List.of("REPLCONF", "ACK", "0"));
        }
        return buildSimpleStrResponse("OK");
    }
}
