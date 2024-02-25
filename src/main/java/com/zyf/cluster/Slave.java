package com.zyf.cluster;

import com.zyf.Constant.Constants;
import com.zyf.ThreadPool;
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
        SlaveHandle slaveHandle = new SlaveHandle(masterClient, inputStream, outputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        slaveHandle.init(reader);
        ThreadPool.execute(slaveHandle::handle);
        try {
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
        } catch (IOException e) {
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
