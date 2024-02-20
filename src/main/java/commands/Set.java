package commands;

import collect.KVString;
import collect.SimpleKVCache;

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
            kvString = new KVString(key, value, Long.parseLong(millSeconds));
        } else {
            System.out.println("set command param:" + key + " " + value);
            kvString = new KVString(key, value);
        }
        SimpleKVCache.put(key, kvString);
        return buildSimpleStrResponse("OK");
    }
}
