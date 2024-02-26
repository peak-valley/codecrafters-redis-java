package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.List;

public class Ping extends AbstractCommand {
    private static final int PING_OFFSET = 14;
    boolean replica = false;
    @Override
    public byte[] execute(List<Object> content) {
        replicaOffset();
        if (replica) {
            replica = false;
            return null;
        }
//
        return buildBulkResponse("PONG");
    }

    private void replicaOffset() {
        String s = ClusterInformation.get(ReplConf.REPLICA_OFFSET);
        if (s == null) {
            return;
        }
        int offset = Integer.parseInt(s) + PING_OFFSET;
        System.out.println("PING offset put: " + offset);
        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
        replica = true;
    }
}
