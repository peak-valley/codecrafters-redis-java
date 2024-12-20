package com.zyf.commands;

import com.zyf.rdb.RDBCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        if (content.size() != 3) {
            return null;
        }

        String param1 = new String((byte[]) content.get(1));
        String param2 = new String((byte[]) content.get(2));
        System.out.println("CONFIG -> " + param1 + ":" + param2);
        if ("GET".equalsIgnoreCase(param1)) {
            String v = RDBCache.get(param2);
            List<Object> list = new ArrayList<>();
            System.out.println("CONFIG value is " + v);
            Collections.addAll(list, param2, v);
            return buildArraysResponse(list);
        }
        System.out.println("CONFIG -> not config");
        return "-ERR\r\n".getBytes();
    }
}
