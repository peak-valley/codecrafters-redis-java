package com.zyf.cluster;

import com.zyf.CommandFactory;
import com.zyf.Constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
        // handshake send ping
        try {
            String command = "*1\r\n$4\r\nping\r\n";
            outputStream.write( command.getBytes());
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
}
