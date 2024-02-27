package com.zyf.commands;

import com.zyf.Constant.Constants;
import com.zyf.collect.SimpleKVCache;
import com.zyf.rdb.RDBCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        if (content.size() <= 2) {
            return null;
        }

        String param1 = new String((byte[]) content.get(1));
        String param2 = new String((byte[]) content.get(2));
        System.out.println("CONFIG -> " + param2 + ":" + param2);
        if ("GET".equals(param1)) {
            String v = RDBCache.get(param2);
            List<Object> list = new ArrayList<>();
            Collections.addAll(list, param1, v);
            return buildArraysResponse(list);
        }

        return new byte[0];
    }
}
