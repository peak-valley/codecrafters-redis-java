package com.zyf.rdb.model;

import com.zyf.collect.KVString;
import com.zyf.collect.SimpleKVCache;

import java.util.List;

public record Rdb(
        List<AuxField> auxFields,
        List<RdbDbInfo> rdbDbInfos
) {
    public void init() {
        for (RdbDbInfo dbInfo : rdbDbInfos) {
            dbInfo.rdbPairs().forEach(rdbPair -> SimpleKVCache.put(rdbPair.key(), new KVString(rdbPair.key(), rdbPair.value())));
//            dbInfo.rdbExpirePairs().forEach(rdbExpirePair -> RedisRepository.setWithExpireTimestamp(rdbExpirePair.key(), rdbExpirePair.value(), rdbExpirePair.expireTime()));
        }
    }
}
