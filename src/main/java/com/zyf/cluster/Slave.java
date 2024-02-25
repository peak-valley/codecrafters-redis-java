package com.zyf.cluster;

import com.zyf.Constant.Constants;
import com.zyf.handle.Handler;
import com.zyf.handle.SlaveHandle;
import com.zyf.infomation.RedisInformation;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.zyf.Protocol.*;

/**
 * Running in slave mode
 */
public class Slave {
    Socket masterClient;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Slave() throws IOException {
        masterClient = new Socket(ClusterInformation.get(Constants.MASTER_HOST), Integer.parseInt(ClusterInformation.get(Constants.MASTER_PORT)));
        this.outputStream = masterClient.getOutputStream();
        this.inputStream = masterClient.getInputStream();
    }

    public void init() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 1.handshake send ping
            System.out.println("send ping to master");
            byte[] b = buildRESPArray(Collections.singletonList("ping"));
            outputStream.write(b);
            print(reader);
            //2.handshake send REPLCONF listening-port <PORT>
            List<String> sendContent = Arrays.asList("REPLCONF", "listening-port", String.valueOf(RedisInformation.getPort()));
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            print(reader);
            // REPLCONF capa psync2
            sendContent = Arrays.asList("REPLCONF", "capa", "psync2");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            print(reader);
            //3.PSYNC ? -1
            sendContent = Arrays.asList("PSYNC", "?", "-1");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            print(reader);
            print(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SlaveHandle slaveHandle = new SlaveHandle(masterClient);
        new Thread(slaveHandle::handle).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(BufferedReader br) throws IOException {
        String s = br.readLine();
        while (Objects.equals(s, "")) {
            s = br.readLine();
        }
        System.out.println(s);
        byte[] bytes = s.getBytes();
        byte b = bytes[0];
        int size = 0;
        int i = 1;
        byte b1 = b;
        switch (b) {
            case DOLLAR_BYTE:
                b1 = bytes[i];
                while (b1 >= 48 && b1 <= 57) {
                    size = size * 10 + b1 - 48;
                    if (++i < bytes.length) {
                        b1 = bytes[i];
                    } else {
                        break;
                    }
                }
                char[] c = new char[size];
                br.read(c, 0, size);
                System.out.println(String.valueOf(c));
                break;
            case START_BYTE:
                b1 = bytes[i];
                while (b1 >= 48 && b1 <= 57) {
                    size = size * 10 + b1 - 48;
                    if (++i < bytes.length) {
                        b1 = bytes[i];
                    } else {
                        break;
                    }
                }
                for (int i1 = 0; i1 < size; i1++) {
                    print(br);
                }
                break;
            case PLUS_BYTE:
                break;
            default:
                throw new IOException("Unknown reply:" + b);
        };
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

    public byte[] buildRESPArray(List<String> content) {
        String ret = "*" + content.size() + "\r\n";
        for (String command : content) {
            ret += "$" + command.length() + "\r\n" + command + "\r\n";
        }
        return ret.getBytes();
    }
}
