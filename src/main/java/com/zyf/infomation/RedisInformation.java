package com.zyf.infomation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisInformation {
    private static Map<String, String> infoMap = new HashMap<>();
    static {
        infoMap.put("role", "master");
        infoMap.put("master_replid", "8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb");
        infoMap.put("master_repl_offset", "0");
    }

    public static String getInfo() {
        StringBuilder ret = new StringBuilder();
        final Set<String> infoSet = infoMap.keySet();
//        if (infoSet.size() == 1) {
//            for (String s : infoSet) {
//                ret.append(s).append(":").append(infoMap.get(s));
//            }
//        } else {
        for (String s : infoSet) {
            ret.append(s).append(":").append(infoMap.get(s)).append("\n");
        }
        return ret.toString();
    }

    public static String setInfo(String k, String v) {
        return infoMap.put(k, v);
    }

    public static String get(String k) {
        return infoMap.get(k);
    }

    public static int port = 6379;

    public static void setPort(int p) {
        port = p;
    }

    public static int getPort() {
        return port;
    }
}
