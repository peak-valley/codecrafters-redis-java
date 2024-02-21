package com.zyf.commands;

import com.zyf.infomation.RedisInformation;

import java.util.List;

public class PSYNC extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {

        return buildSimpleStrResponse("FULLRESYNC " + RedisInformation.get("master_replid") + " 0");
    }
}
