package cluster;

import Constant.Constants;
import infomation.RedisInformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Master {
    Socket client;

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Master() throws IOException {
        client = new Socket(ClusterInformation.get(Constants.MASTER_HOST), Integer.parseInt(ClusterInformation.get(Constants.MASTER_PORT)));
    this.outputStream = client.getOutputStream();
    this.inputStream = client.getInputStream();
    }

    public void init() {
        // handshake
        try {
            outputStream.write("PING".getBytes());
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
