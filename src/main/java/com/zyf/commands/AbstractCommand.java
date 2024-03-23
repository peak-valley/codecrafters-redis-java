package com.zyf.commands;

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
    public byte[] buildSimpleErrResponse(String content) {
        return ("-" + content + "\r\n").getBytes();
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
        System.out.println("build command ->" + command);
        return command.toString().getBytes();
    }

    public byte[] buildArraysResponse(java.util.Set<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            if (o instanceof String o1) {
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }else {
                String o1 = new String((byte[]) o);
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }
        }
        System.out.println("build command ->" + command);
        return command.toString().getBytes();
    }

    public byte[] buildIntegerResponse(int data) {
        String response = ":" + data + _R_N;
        return response.getBytes();
    }

    public String convertByteToString(Object param) {
        return new String((byte[]) param);
    }
}
