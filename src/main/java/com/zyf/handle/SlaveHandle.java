package com.zyf.handle;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;
import com.zyf.ThreadPool;
import com.zyf.infomation.RedisInformation;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.zyf.Protocol.*;

public class SlaveHandle extends AbstractHandler {
    Handler handler;

    public SlaveHandle(Socket clientSocket, InputStream inputStream, OutputStream outputStream) {
        super(clientSocket, inputStream, outputStream);
    }

    public void init(BufferedReader reader) {
        try {

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
            // REPLCONF ACK 0
            outputStream.write(b);
            print(reader);
            print(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ThreadPool.execute(this::handle);
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

    @Override
    public void reply(CommandEnum commandEnum, OutputStream outputStream, byte[] response) throws IOException {
        if (CommandType.WRITE.equals(commandEnum.getType())) {
            System.out.println("The slave's write command cancels the reply");
        } else {
            outputStream.write(response);
        }
    }
}
