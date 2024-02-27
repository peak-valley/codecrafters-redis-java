package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;

import java.util.List;

public class Ping extends AbstractCommand {
    boolean replica = false;
    @Override
    public byte[] execute(List<Object> content) {
//        if (replica) {
//            return null;
//        }
        return buildBulkResponse("PONG");
    }

//    public boolean replica() {
//        int offset = ClusterInformation.getOffset();
//        if (offset == -1) {
//            return true;
//        }
//        return false;
//    }
}
