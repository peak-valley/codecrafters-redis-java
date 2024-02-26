package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.List;

public class Ping extends AbstractCommand {
    boolean replica = false;
    @Override
    public byte[] execute(List<Object> content) {
        return buildBulkResponse("PONG");
    }
}
