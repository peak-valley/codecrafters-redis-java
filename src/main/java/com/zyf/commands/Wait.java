package com.zyf.commands;

import com.zyf.ThreadPool;
import com.zyf.cluster.Master;
import com.zyf.concurrent.GlobalBlocker;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Wait extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        System.out.println("wait is running");

        Master master = Master.getMaster();
        if (!Master.getMaster().presenceSendCommands()) {
            System.out.println("not presence send commands");
            int i = master.slaveSize();
            System.out.println("slave size : " + i);
            return buildIntegerResponse(i);
        }

        GlobalBlocker.await();
        count = new AtomicInteger(0);
        int desireCount = Integer.parseInt(new String((byte[]) content.get(1)));
        long limitTime = Long.parseLong(new String((byte[]) content.get(1)));

        Instant start = Instant.now();



        List<Object> list = new ArrayList<>();
        ThreadPool.execute(() -> {
            System.out.println("Wait -> send REPLCONF command to slave");
            Collections.addAll(list, "REPLCONF", "GETACK", "*");
            master.send(buildArraysResponse(list));
        });

        int currentCount = count.get();
        while (Duration.between(start, Instant.now()).toMillis() > limitTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            if (currentCount >= desireCount) {
                return buildIntegerResponse(currentCount);
            }
        }
        System.out.println("timeout, return current count:" + currentCount);
        return buildIntegerResponse(currentCount);
    }

    static AtomicInteger count = new AtomicInteger(-1);
    public static void incrementAck() {
        if (count.get() == -1) {
            return;
        }
        count.incrementAndGet();
    }
}
