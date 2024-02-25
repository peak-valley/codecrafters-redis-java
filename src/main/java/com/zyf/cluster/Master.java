package com.zyf.cluster;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.zyf.Constant.Constants.*;

/**
 * Running in master mode
 */
public class Master {

    List<Socket> slaveList = new ArrayList<>();

    public void addSlave(String ip, int port) {
        try {
            Socket slave = new Socket(ip, port);
            slaveList.add(slave);
        } catch (IOException e) {
            System.out.println("add slave failed,e:" + e.getMessage());
        }
    }

    public void send(List<Object> data) {
        if(!checkWrite((String) data.get(0))) {
            return;
        }
        byte[] b = buildArraysCommand(data);
        for (Socket socket : slaveList) {
            try(OutputStream outputStream = socket.getOutputStream()) {
                outputStream.write(b);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean checkWrite(String command) {
        CommandEnum commandEnum = CommandEnum.valueOf(command);
        if (CommandType.WRITE.equals(commandEnum.getType())) {
            return true;
        }
        return false;
    }

    public byte[] buildArraysCommand(List<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            String o1 = (String) o;
            command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
        }
        return command.toString().getBytes();
    }
}      
