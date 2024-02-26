package com.zyf.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
            List<Object> c = new ArrayList<>();
            boolean b = Collections.addAll(c, "REPLCONF", "ACK", "0");
            return buildArraysResponse(c);
        }
        return buildSimpleStrResponse("OK");
    }
}
