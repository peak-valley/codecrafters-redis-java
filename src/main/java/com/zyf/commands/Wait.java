package com.zyf.commands;

import com.zyf.ThreadPool;
import com.zyf.cluster.ClusterInformation;
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

        GlobalBlocker.await();
        count = new AtomicInteger(0);
        int desireCount = Integer.parseInt(new String((byte[]) content.get(1)));
        long limitTime = Long.parseLong(new String((byte[]) content.get(2)));

        List<Object> list = new ArrayList<>();
        ThreadPool.execute(() -> {
            System.out.println("Wait -> send REPLCONF command to slave");
            Collections.addAll(list, "REPLCONF", "GETACK", "*");
            master.send(buildArraysResponse(list));
        });
        int offset = ClusterInformation.getOffset();
        if (offset <= 0) {
            System.out.println("not presence send commands");
            int i = master.slaveSize();
            System.out.println("slave size : " + i);
            return buildIntegerResponse(i);
        }

        Instant start = Instant.now();
        int currentCount = 0;
        while (Duration.between(start, Instant.now()).toMillis() < limitTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            currentCount = count.get();
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
