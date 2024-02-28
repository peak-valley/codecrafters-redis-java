package com.zyf.rdb.model;

import com.zyf.collect.KVString;
import com.zyf.collect.SimpleKVCache;

import java.time.Instant;
import java.util.List;

public record Rdb(
        List<AuxField> auxFields,
        List<RdbDbInfo> rdbDbInfos
) {
    public void init() {
        for (RdbDbInfo dbInfo : rdbDbInfos) {
            dbInfo.rdbPairs().forEach(rdbPair -> SimpleKVCache.put(rdbPair.key(), new KVString(rdbPair.key(), rdbPair.value())));
            dbInfo.rdbExpirePairs().forEach(rdbExpirePair -> {
                System.out.println("put expire data, expire:" + Instant.now().toEpochMilli() + " key: " + rdbExpirePair.key());
                SimpleKVCache.put(rdbExpirePair.key(), new KVString(rdbExpirePair.key(), rdbExpirePair.value(), rdbExpirePair.expireTime()));
            });
        }
    }
}
