package com.zyf.cluster;

import com.zyf.CommandFactory;
import com.zyf.Constant.Constants;
import com.zyf.infomation.RedisInformation;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            // 1.handshake send ping
            System.out.println("send ping to master");
            byte[] b = buildRESPArray(Collections.singletonList("ping"));
            outputStream.write(b);
            //2.handshake send REPLCONF listening-port <PORT>
            List<String> sendContent = Arrays.asList("REPLCONF", "listening-port", String.valueOf(RedisInformation.getPort()));
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            // REPLCONF capa psync2
            sendContent = Arrays.asList("REPLCONF", "capa", "psync2");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            //3.PSYNC ? -1
            sendContent = Arrays.asList("PSYNC", "?", "-1");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
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

    public byte[] buildRESPArray(List<String> content) {
        String ret = "*" + content.size() + "\r\n";
        for (String command : content) {
            ret += "$" + command.length() + "\r\n" + command + "\r\n";
        }
        return ret.getBytes();
    }
}
