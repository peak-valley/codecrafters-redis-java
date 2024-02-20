import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Handler {

    public static Map<String, KVString> kvStore = new ConcurrentHashMap<>();

    private final Socket clientSocket;

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {
        try(
                InputStream inputStream = clientSocket.getInputStream();
                final OutputStream outputStream = clientSocket.getOutputStream()
        ) {
            byte[] bs = new byte[1024];
            int read;
            while (true) {
                final Object command = Protocol.process(inputStream);
                byte[] response = null;
                if (command instanceof byte[]) {
                    final byte[] bytes = (byte[]) command;
                    final String content = new String(bytes);
                    if ("PING".equalsIgnoreCase(content.toUpperCase())) {
                        System.out.println("ping command reception");
                        response = buildSimpleStrResponse("PONG");
                    }
                } else if (command instanceof List) {
                    final List commands = (List) command;
                    byte[] bytes = (byte[])commands.get(0);
                    final String content = new String(bytes);
                    if ("ECHO".equalsIgnoreCase(content.toUpperCase())) {
                        response = buildBulkResponse(new String((byte[]) commands.get(1)));
                    } else if ("PING".equalsIgnoreCase(content.toUpperCase())) {
                        response = buildBulkResponse("PONG");
                    } else if ("SET".equalsIgnoreCase(content.toUpperCase())) {
                        System.out.println("set command is running");
                        response = setCommand(commands);
                    } else if ("GET".equalsIgnoreCase(content.toUpperCase())) {
                        System.out.println("get command is running");
                        response = getCommand(commands);
                    }
                }
                outputStream.write(response);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] buildBulkResponse(String content) {
        int length = content.length();
        StringBuilder sb = new StringBuilder();
        String ret = "$" + length + "\r\n" + content + "\r\n";
        System.out.println("build response " + ret);
//        byte[] ret = new byte[]
//        ret.add((byte) '+');
        return ret.getBytes();
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }

    public byte[] setCommand(List<Object> list) {
        if (list == null) {
            return "$-1\r\n".getBytes();
        }
        String key = new String((byte[])list.get(1));
        String value = new String((byte[])list.get(2));
        KVString kvString;
        if (list.size() >= 4) {
            String px = new String((byte[])list.get(3));
            String millSeconds = new String((byte[])list.get(4));

            System.out.println("set command param:" + key + " " + value + " px:" + millSeconds);
            kvString = new KVString(key, value, Long.parseLong(millSeconds));
        } else {
            System.out.println("set command param:" + key + " " + value);
            kvString = new KVString(key, value);
        }
        kvStore.put(key, kvString);
        return buildSimpleStrResponse("OK");
    }

    public byte[] getCommand(List<Object> list) {
        final Object o = list.get(1);
        final byte[] o1 = (byte[]) o;
        final String key = new String((byte[]) list.get(1));
        System.out.println("get command param:" + key);
        final KVString kvString = kvStore.get(key);
        if (kvString == null) {
            return Constants.NULL_BULK_STRING_BYTES;
        }
        if (kvString.expire != null && !isNotExpire(kvString.expire)) {
            System.out.println("get command exec failed,key:" + key + " is expired");
            kvStore.remove(kvString.k);
            return Constants.NULL_BULK_STRING_BYTES;
        } else {
            return buildBulkResponse(kvString.v);
        }
    }

    public static void main(String[] args) {
        System.out.println(isNotExpire(LocalDateTime.now().plusDays(-1)));
    }
    public static boolean isNotExpire(LocalDateTime dateTime) {
        return LocalDateTime.now().compareTo(dateTime) <= 0;
    }
}
