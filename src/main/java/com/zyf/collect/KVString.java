package com.zyf.collect;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class KVString {
    byte[] k;
    byte[] v;
    LocalDateTime expire;

    public KVString(byte[] k, byte[] v) {
        this.k = k;
        this.v = v;
    }

    public KVString(byte[] k, byte[] v, long milliseconds) {
        this.k = k;
        this.v = v;
        final long l = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        this.expire = LocalDateTime.now().plusNanos(TimeUnit.MILLISECONDS.toNanos(milliseconds));
    }

    public byte[] getK() {
        return k;
    }

    public void setK(byte[] k) {
        this.k = k;
    }

    public byte[] getV() {
        return v;
    }

    public void setV(byte[] v) {
        this.v = v;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }
}
