package com.zyf.commands;

import com.zyf.collect.RedisRepository;

import java.util.List;

public class Xadd extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        String key = new String((byte[]) content.get(1));
        String streamId = new String((byte[]) content.get(2));


        RedisRepository.putStream(key, List.of(streamId));
        return buildBulkResponse(streamId);
    }
}
