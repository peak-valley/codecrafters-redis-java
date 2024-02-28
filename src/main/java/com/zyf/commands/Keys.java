package com.zyf.commands;

import com.zyf.collect.SimpleKVCache;

import java.util.*;
import java.util.Set;

public class Keys extends AbstractCommand{
    @Override
    public byte[] execute(List<Object> content) {
        if (content.size() != 2) {
            return null;
        }
        Set<String> keys = SimpleKVCache.keys();
        List<Object> keyList = new ArrayList<>(keys);
        return buildArraysResponse(keyList);
    }
}
