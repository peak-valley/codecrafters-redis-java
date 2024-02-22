package com.zyf;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

public class TestClient {
    static int mainPort = 6380;
    public static void main(String[] args) {
//        server();
        client();
    }
// --replicaof localhost 6379
    private static void server() {
        try {
            ServerSocket serverSocket = new ServerSocket(6379);
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            int read;

            while ((read = inputStream.read(b)) > 0) {
                String s = new String(Arrays.copyOf(b, read));
                System.out.println(s);
                if (s.contains("PSYNC")) {
                    outputStream.write("+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n".getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void client() {
        try (
                Socket client = new Socket("localhost", mainPort);
                final OutputStream outputStream = client.getOutputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            for (int i = 0; i < 1; i++) {
//                pong(outputStream, bufferedReader);
//                ping(outputStream, bufferedReader);
//                echo(outputStream, bufferedReader);
//                setExpire(outputStream, bufferedReader);
//                set(outputStream, bufferedReader);
//                get(outputStream, bufferedReader);
//                info(outputStream, bufferedReader);
                psync(outputStream, bufferedReader);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void psync(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        List<String> sendContent = Arrays.asList("PSYNC", "?", "-1");
        System.out.println("send " + sendContent + " to master");
        outputStream.write(buildRESPArray(sendContent));
    }

    private static void pong(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*1\r\n$4\r\npong\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
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

    public static byte[] buildRESPArray(List<String> content) {
        String ret = "*" + content.size() + "\r\n";
        for (String command : content) {
            ret += "$" + command.length() + "\r\n" + command + "\r\n";
        }
        return ret.getBytes();
    }

}
