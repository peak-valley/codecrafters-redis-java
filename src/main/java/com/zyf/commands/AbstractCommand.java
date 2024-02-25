package com.zyf.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.zyf.Constant.Constants._R_N;

public abstract class AbstractCommand implements Command {
    public byte[] buildBulkResponse(String content) {
        int length = content.length();
        StringBuilder sb = new StringBuilder();
        String ret = "$" + length + "\r\n" + content + "\r\n";
        System.out.println("build response " + ret);
//        byte[] ret = new byte[]
//        ret.add((byte) '+');
        return ret.getBytes();
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }

    public byte[] buildArraysResponse(List<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            String o1 = new String((byte[]) o);
            command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
        }
        System.out.println("build command ->" + command);
        return command.toString().getBytes();
    }

    public static void main(String[] args) {
        ReplConf replConf = new ReplConf();
        List<Object> objects = new ArrayList<>();
        objects.add("REPLCONF".getBytes());
        objects.add("GETACK".getBytes());
        objects.add("*".getBytes());
        System.out.println(replConf.buildArraysResponse(objects));
    }
}
