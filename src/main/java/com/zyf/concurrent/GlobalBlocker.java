package com.zyf.concurrent;

public class GlobalBlocker {

    static volatile boolean flag = false;

    public static void await() {
        try {
            while (flag) {
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pass() {
        flag = false;
    }

    public static void block() {
        flag = true;
    }
}
