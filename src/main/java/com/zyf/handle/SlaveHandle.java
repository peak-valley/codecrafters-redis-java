package com.zyf.handle;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;
import com.zyf.ThreadPool;
import com.zyf.cluster.ClusterInformation;
import com.zyf.commands.ReplConf;
import com.zyf.infomation.RedisInformation;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import static com.zyf.Constant.Constants._R_N;
import static com.zyf.Protocol.*;

public class SlaveHandle extends AbstractHandler {
    Handler handler;
    Semaphore semaphore = new Semaphore(0);

    public SlaveHandle(Socket clientSocket, InputStream inputStream, OutputStream outputStream) {
        super(clientSocket, inputStream, outputStream);
    }

    public void init() {
        ThreadPool.execute(this::handle);
        try {

            // 1.handshake send ping
            System.out.println("send ping to master");
            byte[] b = buildRESPArray(Collections.singletonList("ping"));
            outputStream.write(b);
            print(inputStream);
            //2.handshake send REPLCONF listening-port <PORT>
            List<String> sendContent = Arrays.asList("REPLCONF", "listening-port", String.valueOf(RedisInformation.getPort()));
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            print(inputStream);
            // REPLCONF capa psync2
            sendContent = Arrays.asList("REPLCONF", "capa", "psync2");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            outputStream.write(b);
            print(inputStream);
            //3.PSYNC ? -1
            sendContent = Arrays.asList("PSYNC", "?", "-1");
            System.out.println("send " + sendContent + " to master");
            b = buildRESPArray(sendContent);
            // REPLCONF ACK 0
            outputStream.write(b);
            print(inputStream);
            print(inputStream);
            semaphore.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.handle();
    }

    @Override
    public void afterExecuting(CommandEnum commandEnum, OutputStream outputStream, Object data, byte[] response) {
        String s = ClusterInformation.get(ReplConf.REPLICA_OFFSET);
        if (s == null) {
            return;
        }
        if (CommandEnum.REPLCONF.equals(commandEnum)) {
            return;
        }
        if (!(data instanceof List)) {
            return;
        }
        List listData = (List) data;
//        byte[] bytes = buildArraysResponse(listData);
        int offset = Integer.parseInt(s) + listData.size();
        System.out.println(commandEnum.getName() + " offset add: " + offset);
        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
    }

    public byte[] buildArraysResponse(List<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            if (o instanceof String) {
                String o1 = (String) o;
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }else {
                String o1 = new String((byte[]) o);
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }
        }
        System.out.println("build command ->" + command);
        return command.toString().getBytes();
    }

    public void print(InputStream is) throws IOException {
        String s = readLine(is);
        while (Objects.equals(s, "")) {
            s = readLine(is);
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
                byte[] c = new byte[size];
                is.read(c, 0, size);
                System.out.println(new String(c));
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
                    print(is);
                }
                break;
            case PLUS_BYTE:
                break;
            default:
                throw new IOException("Unknown reply:" + b);
        };
    }

    public String readLine(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1];
        int read;
        while ((read = is.read(bytes, 0, 1)) > 0) {
            if (bytes[0] == '\r') {
                continue;
            }
            if (bytes[0] == '\n') {
                break;
            }
            sb.append(new String(bytes));
        }
        return sb.toString();
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
