package com.zyf.collect;

public class KVString {
    String k;
    String v;
    Long expire;

    public KVString(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public KVString(String k, String v, long milliseconds) {
        this.k = k;
        this.v = v;
        this.expire = milliseconds;
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

    public Long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
