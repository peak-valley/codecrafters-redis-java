package com.zyf.commands;

import com.zyf.Constant.Constants;
import com.zyf.collect.SimpleKVCache;
import com.zyf.rdb.RDBCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CONFIG extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        if (content.size() <= 2) {
            return null;
        }

        String param1 = new String((byte[]) content.get(1));
        String param2 = new String((byte[]) content.get(2));
        if ("GET".equals(param1)) {
            if (Constants.RDB_DIR.equals(param2)) {
                String dir = RDBCache.get(Constants.RDB_DIR);
                List<Object> list = new ArrayList<>();
                Collections.addAll(list, Constants.RDB_DIR, dir);
                return buildArraysResponse(list);
            } else if (Constants.RDB_FILENAME.equals(param2)) {
                String v = RDBCache.get(Constants.RDB_FILENAME);
                List<Object> list = new ArrayList<>();
                Collections.addAll(list, Constants.RDB_FILENAME, v);
                return buildArraysResponse(list);
            }
        }

        return new byte[0];
    }
}
