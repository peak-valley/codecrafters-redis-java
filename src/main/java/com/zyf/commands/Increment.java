package com.zyf.commands;

import com.zyf.collect.KVString;
import com.zyf.collect.SimpleKVCache;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *
 * The <a href=https://redis.io/docs/latest/commands/incr/>INCR</a> command is used to increment the value of a key by 1.
 */
@Slf4j
public class Increment extends AbstractCommand {
    private final String ERR_NOT_AN_INTEGER = "ERR value is not an integer or out of range";
    @Override
    public byte[] execute(List<Object> content) {
        String key = new String((byte[])content.get(1));
        log.info("incr:key:{}", key);

        KVString kvString = SimpleKVCache.get(key);
        if (kvString == null) {
            KVString save = new KVString(key, "1");
            SimpleKVCache.put(save.getK(), save);
            return buildIntegerResponse(1);
        }
        if (!checkIsInteger(kvString.getV())) {
            return buildSimpleErrResponse(ERR_NOT_AN_INTEGER);
        }
        String vStr = kvString.getV();
        int v = Integer.parseInt(vStr);
        ++v;
        kvString.setV(String.valueOf(v));
        SimpleKVCache.put(kvString.getK(), kvString);
        return buildIntegerResponse(v);
    }

    public boolean checkIsInteger(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
