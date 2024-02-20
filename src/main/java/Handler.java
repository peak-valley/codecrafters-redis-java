import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Handler {

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
}
