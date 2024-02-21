package com.zyf.collect;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class KVString {
    String k;
    String v;
    LocalDateTime expire;

    public KVString(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public KVString(String k, String v, long milliseconds) {
        this.k = k;
        this.v = v;
        final long l = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        this.expire = LocalDateTime.now().plusNanos(TimeUnit.MILLISECONDS.toNanos(milliseconds));
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

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }
}
