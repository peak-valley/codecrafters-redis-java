package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.List;

public class Ping extends AbstractCommand {
    int PING_OFFSET = 14;
    @Override
    public byte[] execute(List<Object> content) {
        setSlaveOffset();
        return buildBulkResponse("PONG");
    }

    private void setSlaveOffset() {
        String s = ClusterInformation.get(ReplConf.REPLICA_OFFSET);
        if (s == null) {
            return;
        }

        int offset = Integer.parseInt(s) + 14;
        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
    }
}
