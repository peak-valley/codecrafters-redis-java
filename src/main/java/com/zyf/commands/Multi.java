package com.zyf.commands;

import com.zyf.collect.RedisRepository;

import java.util.List;

public class Multi extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        RedisRepository.switchMulti(true);
        return buildSimpleStrResponse("OK");
    }
}
