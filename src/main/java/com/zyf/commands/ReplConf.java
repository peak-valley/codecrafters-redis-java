package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;
import com.zyf.handle.SlaveHandle;

import java.util.ArrayList;
import java.util.Collection;
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
        }
        return buildSimpleStrResponse("OK");
    }

    private String getOffset() {
        String s = ClusterInformation.get(REPLICA_OFFSET);
        if (s == null) {
            ClusterInformation.put(REPLICA_OFFSET, "0");
            return "0";
        }
        synchronized (SlaveHandle.class) {
            int offset = Integer.parseInt(s);
            String ret = String.valueOf(offset + 37);
            ClusterInformation.put(REPLICA_OFFSET, ret);
            return ret;
        }
    }
}
