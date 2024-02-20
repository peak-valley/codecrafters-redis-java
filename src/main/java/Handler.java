import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
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
        System.out.print("set command param:");
        for (Object o : list) {
            System.out.print(new String((byte[]) o));
        }
        System.out.println();
        final int size = list.size();
        final Object o = list.get(1);
        final byte[] o1 = (byte[]) o;
        final String string = new String(o1);
        final String[] s = string.split(" ");
        String key = s[0];
        String value = s[1];
        KVString kvString = new KVString(key, value);
        System.out.println("put,k:" + key + ",v:" + value);
        kvStore.put(key, kvString);
        return buildSimpleStrResponse("OK");
    }

    public byte[] getCommand(List<Object> list) {
        final int size = list.size();
        final Object o = list.get(1);
        final byte[] o1 = (byte[]) o;
        final String key = new String(o1);
        final KVString kvString = kvStore.get(key);
        if (kvString == null) {
            return buildBulkResponse(null);
        }
        return buildBulkResponse(kvString.v);
    }
}
