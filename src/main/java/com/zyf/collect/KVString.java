package com.zyf.collect;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class KVString {
    String k;
    String v;
    long expire = -1;

    public KVString(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public KVString(String k, String v, long milliseconds) {
        this.k = k;
        this.v = v;
        var instant = Instant.now();
        this.expire = instant.plus(milliseconds, ChronoUnit.MILLIS).toEpochMilli();
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
