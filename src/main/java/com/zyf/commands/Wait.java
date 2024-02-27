package com.zyf.commands;

import com.zyf.cluster.Master;
import com.zyf.concurrent.GlobalBlocker;

import java.util.Collections;
import java.util.List;

public class Wait extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("wait is running");
        String s = new String((byte[]) content.get(2));
        long ms = Long.parseLong(s);
        GlobalBlocker.await();
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = Master.getMaster().slaveSize();
        return buildIntegerResponse(i);
    }
}
