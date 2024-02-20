import sun.util.locale.provider.TimeZoneNameUtility;

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
}
