package com.zyf.commands;

import com.zyf.cluster.Master;
import com.zyf.concurrent.GlobalBlocker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wait extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("wait is running");

        GlobalBlocker.await();
        Master master = Master.getMaster();
        List<Object> list = new ArrayList<>();
        System.out.println("Wait -> send REPLCONF command to slave");
        Collections.addAll(list, "REPLCONF", "GETACK", "*");
        master.send(buildArraysResponse(list));

        int i = master.slaveSize();
        byte[] bytes = buildIntegerResponse(i);
        if (!Master.getMaster().presenceSendCommands()) {
            System.out.println("not presence send commands");
            System.out.println("slave size : " + i);
            return bytes;
        }
        String s = new String((byte[]) content.get(2));
        long ms = Long.parseLong(s);
        try {

            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = Master.getMaster().slaveSize();
        bytes = buildIntegerResponse(i);
        return bytes;
    }
}
