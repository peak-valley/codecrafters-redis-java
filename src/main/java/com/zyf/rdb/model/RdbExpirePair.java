package com.zyf.rdb.model;

public record RdbExpirePair(
        long expireTime,
        String key,
        String value
) {
}
