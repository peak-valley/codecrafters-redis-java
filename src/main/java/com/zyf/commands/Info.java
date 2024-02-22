package com.zyf.commands;

import com.zyf.infomation.RedisInformation;

import java.util.List;

public class Info extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("info command is running");
        return buildBulkResponse(RedisInformation.getInfo());
    }
}
