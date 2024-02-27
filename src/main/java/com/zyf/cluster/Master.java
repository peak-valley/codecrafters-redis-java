package com.zyf.cluster;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;
import com.zyf.concurrent.GlobalBlocker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zyf.Constant.Constants.*;

/**
 * Running in master mode
 */
public class Master {
    private volatile static Master MY = null;
    List<OutputStream> slaveList = new ArrayList<>();
    AtomicInteger sendCommands = new AtomicInteger(0);

    public static boolean moasterMode = true;

    public static Master getMaster() {
        return MY;
    }

    public static synchronized void init() {
        if (MY == null) {
            MY = new Master();
        }
    }

    public int slaveSize() {
        return slaveList.size();
    }

    public void addSlave(String ip, int port, OutputStream os) {
        System.out.println("connect slave->" + ip + ":" + port);
        slaveList.add(os);
    }

    public void send(byte[] command) {
        if (slaveList.size() < 1) {
            System.out.println("not slave node");
            GlobalBlocker.pass();
            return;
        }

        for (OutputStream outputStream : slaveList) {
            try {
                outputStream.write(command);
            } catch (IOException e) {
                slaveList.remove(outputStream);
                System.out.println("slave is dropped, removed");
            }
        }
    }

    public void send(List<Object> data, String command) {
        if(!checkWrite(new String((byte[]) data.get(0)))) {
            return;
        }
        if (slaveList.size() < 1) {
            System.out.println("not slave node");
            GlobalBlocker.pass();
            return;
        }
        System.out.printf("start send " + command);
        sendCommands.incrementAndGet();
        byte[] b = buildArraysCommand(data);
        for (int i = 0; i < slaveList.size(); i++) {
            System.out.println("sending " + i + " command to slave");
            OutputStream os = slaveList.get(i);
            try {
                os.write(b);
            } catch (IOException e) {
                slaveList.remove(os);
                System.out.println("slave is dropped, removed");
            }

        }
        for (OutputStream os : slaveList) {
        }
        sendCommands.decrementAndGet();
        GlobalBlocker.pass();
    }

    public boolean checkWrite(String command) {
        CommandEnum commandEnum = CommandEnum.valueOf(command.toUpperCase());
        if (CommandType.WRITE.equals(commandEnum.getType())) {
            return true;
        }
        return false;
    }

    public byte[] buildArraysCommand(List<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            String o1 = new String((byte[]) o);
            command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
        }
        System.out.println("build command ->" + command);
        return command.toString().getBytes();
    }
}      
