package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplConf extends AbstractCommand {
    public static final String REPLICA_OFFSET = "replicaOffset";
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("REPLCONF is running");
        if (content.size() < 2) {
            return buildSimpleStrResponse("OK");
        }
        String param1 = new String((byte[]) content.get(1));
        if ("GETACK".equals(param1)) {
            List<Object> c = new ArrayList<>();
            boolean b = Collections.addAll(c, "REPLCONF", "ACK", getOffset());
            return buildArraysResponse(c);
        } else if ("ACK".equals(param1)) {
            System.out.println("ReplConf -> recevied ACK");
            Wait.incrementAck();
        }
        return buildSimpleStrResponse("OK");
    }

    private String getOffset() {
        int offset = ClusterInformation.getOffset();
        if (offset == -1) {
            ClusterInformation.offset(1);
            return "0";
        }
        String ret = String.valueOf(ClusterInformation.offset(37));
        return ret;
    }
}
