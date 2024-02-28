package com.zyf.commands;

import com.zyf.cluster.ClusterInformation;
import com.zyf.collect.KVString;
import com.zyf.collect.SimpleKVCache;

import java.time.Instant;
import java.util.List;

public class Set extends AbstractCommand {

    @Override
    public byte[] execute(List<Object> content) {
        if (content == null) {
            return "$-1\r\n".getBytes();
        }
        String key = new String((byte[])content.get(1));
        String value = new String((byte[])content.get(2));
        KVString kvString;
        if (content.size() >= 4) {
            String px = new String((byte[])content.get(3));
            String millSeconds = new String((byte[])content.get(4));

            System.out.println("set command param:" + key + " " + value + " px:" + millSeconds);
            kvString = new KVString(key, value, Instant.now().toEpochMilli() + Long.parseLong(millSeconds));
        } else {
            System.out.println("set command param:" + key + " " + value);
            kvString = new KVString(key, value);
        }
        SimpleKVCache.put(key, kvString);

//        setSlaveOffset(content);

        return buildSimpleStrResponse("OK");
    }

//    private void setSlaveOffset(List<Object> content) {
//        String s = ClusterInformation.get(ReplConf.REPLICA_OFFSET);
//        if (s == null) {
//            return;
//        }
//        byte[] bytes = buildArraysResponse(content);
//        int offset = Integer.parseInt(s) + bytes.length;
//        System.out.println("Set offset add: " + offset);
//        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
//    }
}
