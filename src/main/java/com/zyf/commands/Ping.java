package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.List;

public class Ping extends AbstractCommand {
    int PING_OFFSET = 14;
    boolean replica = false;
    @Override
    public byte[] execute(List<Object> content) {
        setSlaveOffset();
        if (replica) {
            replica = false;
            return null;
        }
        return buildBulkResponse("PONG");
    }

    private void setSlaveOffset() {
        String s = ClusterInformation.get(ReplConf.REPLICA_OFFSET);
        if (s == null) {
            return;
        }
        replica = true;
        int offset = Integer.parseInt(s) + PING_OFFSET;
        System.out.println("PING offset put: " + offset);
        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
    }
}
