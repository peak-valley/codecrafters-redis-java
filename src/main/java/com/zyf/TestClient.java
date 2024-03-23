package com.zyf;

import com.zyf.Constant.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClient {
    static int mainPort = 6380;
    static ExecutorService executorService;
    static Socket client = null;
    static OutputStream outputStream = null;
    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(1);

//        server();
        client();
    }
// --replicaof localhost 6379
    private static void server() {
        try {
            ServerSocket serverSocket = new ServerSocket(mainPort);
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            byte[] b = new byte[1024];
            int read;
            boolean psync = false;
            int i = 0;
            while ((read = inputStream.read(b)) > 0) {
                String s = new String(Arrays.copyOf(b, read));
                System.out.println(s);
                if (s.contains("ping")) {
                    pong(outputStream, bufferedReader);
                } else if (s.contains("REPLCONF")) {
                    if (s.contains("capa") || s.contains("listening-port")) {
                        outputStream.write("+OK\r\n".getBytes());
                    }
                    if (s.contains("ACK")) {
                        if (i == 0) {
                            pingNotRead(outputStream, bufferedReader);
                            outputStream.write("*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n".getBytes());
                            i++;
                        } else if (i == 1) {
                            i++;
//                            set(outputStream, bufferedReader, "l27x2yrsav8", "dy85j0bw6sx2");
//                            set(outputStream, bufferedReader, "xhwi9x5791fsstzf", "7ekoe2");
//                            outputStream.write("*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n".getBytes());
//                        } else if (i == 2) {
//                            i++;
//                            outputStream.write("*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n".getBytes());
                        }
//                        ping(outputStream, bufferedReader);
                    }
                } else if (s.contains("PSYNC")) {
                    outputStream.write("+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n".getBytes());
                    byte[] decodeDB = Base64.getDecoder().decode(Constants.EMPTY_RDB_BASE64);
                    String prefix = "$" + decodeDB.length + "\r\n";

                    outputStream.write(prefix.getBytes());
                    outputStream.write(decodeDB);
                    psync = true;
//                    ping(outputStream, bufferedReader);
                    outputStream.write("*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n".getBytes());
                }
//                if (psync) {
//                    String s1 = "";
////                    s1 = bufferedReader.readLine();
////                    System.out.println(s1);
//                    set(outputStream, bufferedReader, "foo", "123");
//                    Socket socket1 = new Socket("localhost", 6379);
//                    OutputStream outputStream1 = socket1.getOutputStream();
//                    InputStream inputStream1 = socket1.getInputStream();
//                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream));
//                    set(outputStream1, bufferedReader1, "bar", "2123");
//                    set(outputStream1, bufferedReader1, "baz", "24523");
////
//                    get(outputStream1, bufferedReader1, "foo");
//                    new Thread(() -> print(bufferedReader)).start();
//                    new Thread(() -> print(bufferedReader1)).start();
//                    try {
//                        Thread.sleep(100000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                get(outputStream, bufferedReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void client() {
        try {
                    try {
            client = new Socket("localhost", mainPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
                outputStream = client.getOutputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            for (int i = 0; i < 1; i++) {
//                concurrentXadd(outputStream, bufferedReader);
//                xadd("mysteream1",outputStream, bufferedReader);
//                xadd("mysteream1",outputStream, bufferedReader);
                String key = "blueberry", streamId = "0-3";
                xadd1(key, streamId,outputStream, bufferedReader);
                xreadBlock2(key, streamId, outputStream, bufferedReader);
//                xrange(outputStream, bufferedReader);
//                pong(outputStream, bufferedReader);
//                ping(outputStream, bufferedReader);
//                echo(outputStream, bufferedReader);
//                setExpire(outputStream, bufferedReader);
//                setReadReply(outputStream, bufferedReader, "foo", "123");
//                wait(outputStream, bufferedReader);
//                set(outputStream, bufferedReader, "bar", "2123");
//                set(outputStream, bufferedReader, "foo", "24523");
//                get(outputStream, bufferedReader);
//                info(outputStream, bufferedReader);
                new Thread(() -> print(bufferedReader)).start();
//                psync(outputStream, bufferedReader);

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void print(BufferedReader br) {
        String s;
        while (true) {
            try {
                if ((s = br.readLine()) == null) break;
                System.out.println(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void xreadBlock2(String key, String streamId, OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*6\r\n$5\r\nxread\r\n$5\r\nblock\r\n$5\r\n20000\r\n$7\r\nstreams\r\n$"+key.length()+"\r\n"+key+"\r\n$"+streamId.length()+"\r\n"+streamId+"\r\n";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
    }
    private static void xreadBlock(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*8\r\n$5\r\nxread\r\n$5\r\nblock\r\n$5\r\n20000\r\n$7\r\nstreams\r\n$10\r\nmysteream1\r\n$10\r\nmysteream2\r\n$3\r\n1-4\r\n$1\r\n0\r\n";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
    }


    private static void xadd1(String key, String streamId, OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*5\r\n$4\r\nxadd\r\n$" + key.length() + "\r\n"+ key +"\r\n$"+streamId.length()+"\r\n" + streamId + "\r\n$1\r\n1\r\n$1\r\n1\r\n";
        outputStream.write(command.getBytes());
    }
    private static void xread(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*6\r\n$5\r\nxread\r\n$7\r\nstreams\r\n$10\r\nmysteream1\r\n$10\r\nmysteream2\r\n$1\r\n0\r\n$1\r\n0\r\n";
        outputStream.write(command.getBytes());
    }

    private static void xrange(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*4\r\n$6\r\nxrange\r\n$8\r\nmystream\r\n$1\r\n1\r\n$1\r\n2\r\n";
//        String command = "*5\r\n$4\r\nxadd\r\n$8\r\nmystream\r\n$3\r\n1-*\r\n$1\r\n1\r\n$1\r\n1\r\n";
//        String command = "*5\r\n$4\r\nxadd\r\n$8\r\nmystream\r\n$3\r\n0-0\r\n$1\r\n1\r\n$1\r\n1\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
    }

    private static void concurrentXadd(OutputStream outputStream, BufferedReader bufferedReader) {
        CountDownLatch latch = new CountDownLatch(1);
        Thread thread1 = new Thread(() -> {
            try {
                xadd(outputStream, bufferedReader);
                latch.await();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                xadd(outputStream, bufferedReader);
                latch.await();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
    }
    private static void xadd(String key, OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*5\r\n$4\r\nxadd\r\n$" + key.length() + "\r\n"+ key +"\r\n$3\r\n1-1\r\n$1\r\n1\r\n$1\r\n1\r\n";
        outputStream.write(command.getBytes());
    }
    private static void xadd(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
//        String command = "*5\r\n$4\r\nxadd\r\n$8\r\nmystrea3\r\n$1\r\n*\r\n$1\r\n1\r\n$1\r\n1\r\n";
//        String command = "*5\r\n$4\r\nxadd\r\n$8\r\nmystream\r\n$3\r\n1-*\r\n$1\r\n1\r\n$1\r\n1\r\n";
        String command = "*5\r\n$4\r\nxadd\r\n$8\r\nmystream\r\n$3\r\n2-2\r\n$1\r\n1\r\n$1\r\n1\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
    }

    private static void wait(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*3\r\n$4\r\nwait\r\n$1\r\n1\r\n$3\r\n500\r\n";
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

    private static void get(OutputStream outputStream, BufferedReader bufferedReader, String k) throws IOException {
        String command = "*2\r\n$3\r\nget\r\n$"+ k.length() +"\r\n" + k + "\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
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

    private static void setReadReply(OutputStream outputStream, BufferedReader bufferedReader, String k, String v) throws IOException {
        String command = "*3\r\n$3\r\nset\r\n$"+k.length()+"\r\n" + k + "\r\n$"+v.length()+"\r\n"+v+"\r\n";
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

    private static void set(OutputStream outputStream, BufferedReader bufferedReader, String k, String v) throws IOException {
        String command = "*3\r\n$3\r\nset\r\n$"+k.length()+"\r\n" + k + "\r\n$"+v.length()+"\r\n"+v+"\r\n";
//                String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
//        char[] c;
//        int len = bufferedReader.read();
//        c = new char[len];
//        bufferedReader.read(c);
//        final String s = new String(c);
//        System.out.println(s);
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
    private static void pingNotRead(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        String command = "*1\r\n$4\r\nping\r\n";
//                String command = "*2\r\n$4\r\necho\r\n$3\r\nhey\r\n";
//                String ping = "*2\r\n$4\r\necho\r\n$3";
        outputStream.write(command.getBytes());
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
