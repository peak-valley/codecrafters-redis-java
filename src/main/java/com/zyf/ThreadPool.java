package com.zyf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    final static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void execute(Runnable r) {
        executorService.submit(r);
    }
}
