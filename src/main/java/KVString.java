import java.time.LocalDateTime;

public class KVString {
    String k;
    String v;
    LocalDateTime expire;

    public KVString(String k, String v) {
        this.k = k;
        this.v = v;
    }
}
