package commands;

import Constant.Constants;
import collect.KVString;
import collect.SimpleKVCache;

import java.time.LocalDateTime;
import java.util.List;

public class Get extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("get data is running");
        final Object o = content.get(1);
        final byte[] o1 = (byte[]) o;
        final String key = new String((byte[]) content.get(1));
        System.out.println("get command param:" + key);
        final KVString kvString = SimpleKVCache.get(key);
        if (kvString == null) {
            return Constants.NULL_BULK_STRING_BYTES;
        }
        if (kvString.getExpire() != null && !isNotExpire(kvString.getExpire())) {
            System.out.println("get command exec failed,key:" + key + " is expired");
            SimpleKVCache.remove(kvString.getK());
            return Constants.NULL_BULK_STRING_BYTES;
        } else {
            return buildBulkResponse(kvString.getV());
        }
    }

    public static boolean isNotExpire(LocalDateTime dateTime) {
        return LocalDateTime.now().compareTo(dateTime) <= 0;
    }
}
