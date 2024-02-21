package infomation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisInformation {
    private static Map<String, String> infoMap = new HashMap<>();
    static {
        infoMap.put("role", "master");
    }

    public static String getInfo() {
        StringBuilder ret = new StringBuilder();
        final Set<String> infoSet = infoMap.keySet();
        if (infoSet.size() == 1) {
            for (String s : infoSet) {
                ret.append(s).append(":").append(infoMap.get(s));
            }
        } else {
            for (String s : infoSet) {
                ret.append(s).append(":").append(infoMap.get(s)).append("\n");
            }
        }
        return ret.toString();
    }

    public static String setInfo(String k, String v) {
        return infoMap.put(k, v);
    }

    public static int port = 6379;

    public static void setPort(int p) {
        port = p;
    }

    public static int getPort() {
        return port;
    }
}
