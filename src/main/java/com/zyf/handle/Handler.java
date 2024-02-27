package com.zyf.handle;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;
import com.zyf.cluster.ClusterInformation;
import com.zyf.commands.ReplConf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import static com.zyf.Constant.Constants._R_N;

public class Handler extends AbstractHandler {

    public Handler(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void afterExecuting(CommandEnum commandEnum, OutputStream outputStream, Object data, byte[] response) {
        if (!commandEnum.getType().equals(CommandType.WRITE)) {
            return;
        }
        if (!(data instanceof List)) {
            return;
        }

        List listData = (List) data;
        byte[] bytes = buildArraysResponse(listData);
        int offset = ClusterInformation.getOffset();
        if (offset == -1) {
            offset++;
        }
        offset += ClusterInformation.offset(bytes.length);
        System.out.println(commandEnum.getName() + " offset add: " + bytes.length);
        ClusterInformation.put(ReplConf.REPLICA_OFFSET, String.valueOf(offset));
    }

    @Override
    public void reply(CommandEnum commandEnum, OutputStream outputStream, byte[] response) throws IOException {
        System.out.println("reply -> " + commandEnum.getName());
        outputStream.write(response);
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
        return command.toString().getBytes();
    }

    @Override
    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
