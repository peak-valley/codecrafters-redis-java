package com.zyf.commands;

import com.zyf.collect.KVString;
import com.zyf.collect.SimpleKVCache;

import java.util.List;

public class Type extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        String key = new String((byte[]) content.get(1));
        KVString kvString = SimpleKVCache.get(key);
        if (kvString != null) {
            return buildSimpleStrResponse("string");
        }
        return buildSimpleStrResponse("none");
    }
}
