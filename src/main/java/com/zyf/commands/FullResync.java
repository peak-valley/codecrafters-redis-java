package com.zyf.commands;

import com.zyf.rdb.RdbUtil;

import java.util.List;

public class FullResync extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        return RdbUtil.EMPTY_RDB_HEX.getBytes();
    }
}
