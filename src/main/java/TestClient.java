import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;

public class TestClient {
    public static void main(String[] args) {
        try (
                Socket client = new Socket("localhost", 6379);
                final OutputStream outputStream = client.getOutputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            for (int i = 0; i < 1; i++) {
                ping(outputStream, bufferedReader);
                echo(outputStream, bufferedReader);
                setExpire(outputStream, bufferedReader);
                set(outputStream, bufferedReader);
                get(outputStream, bufferedReader);
                info(outputStream, bufferedReader);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void info(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*2\r\n$4\r\ninfo\r\n$11\r\nreplication\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }

    private static void get(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*2\r\n$3\r\nget\r\n$6\r\nmangos\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }
    private static void setExpire(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*5\r\n$3\r\nset\r\n$6\r\nmangos\r\n$5\r\nhello\r\n$2\r\npX\r\n$4\r\n9000\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }

    private static void set(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*3\r\n$3\r\nset\r\n$6\r\nmangos\r\n$5\r\nhello\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }
    private static void ping(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }

    private static void echo(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
        int len = bufferedReader.read();
        c = new char[len];
        bufferedReader.read(c);
        final String s = new String(c);
        System.out.println(s);
    }
}
