package com.zyf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * see Jedis com.zyf.Protocol
 *
 * @link
 * <a href="https://github.com/redis/jedis/blob/11a4513ff9581a40530a84e6c8ee019c4a3f9e38/src/main/java/redis/clients/jedis/Protocol.java">protocol</a>
 */
public class Protocol {
    public static final byte DOLLAR_BYTE = '$';
    public static final byte START_BYTE = '*';
    public static final byte PLUS_BYTE = '+';

    public static Object process(final InputStream inputStream) throws IOException {
        return process(inputStream, false);
    }

    public static Object process(InputStream inputStream, boolean recursive) throws IOException {
        final Byte aByte = readByte(inputStream);
        if (aByte == null) {
            return null;
        }
        byte b = aByte;
        System.out.println("process read b:" + Arrays.toString(Character.toChars(b)));
        return switch (b) {
            case DOLLAR_BYTE -> processBulkReply(inputStream, recursive);
            case START_BYTE -> processMultiBulkReply(inputStream);
            case PLUS_BYTE -> processSimpleReply(inputStream);
            default -> throw new IOException("Unknown reply:" + b);
        };
    }

    public static Object processSimpleReply(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("processSimpleReply is failed:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Object processMultiBulkReply(InputStream inputStream) {
        byte b = readByte(inputStream);
        int value = 0;
        while (b >= 48 && b <= 57) {
            value = value * 10 + value + b - 48;
            b = readByte(inputStream);
        }
        System.out.println("multiBulk has " + value + " elements");
        // 再读一个 \n
        readByte(inputStream);
        List<Object> ret = new ArrayList<>(value);
        for (int i = 0; i < value; i++) {
            try {
                ret.add(process(inputStream, true));
            } catch (IOException e) {
                System.out.println("processMultiBulkReply is failed,msg:"+e.getMessage());
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static Object processBulkReply(InputStream inputStream, boolean recursive) {
        byte b = readByte(inputStream);
        // 
        int value = 0;
        while (b >= 48 && b <= 57) {
            value = value * 10 + b - 48;
            b = readByte(inputStream);
        }
        // 再读一个\n
        readByte(inputStream);
        final byte[] read = new byte[value];
        // 读具体的字符
        int offset = 0;
        try {
            inputStream.read(read, 0, value);
        } catch (IOException e) {
            System.out.println("读取Bulk失败,msg:"+e.getMessage());
        }
        if (recursive) {
            readByte(inputStream);
            readByte(inputStream);
        }

        return read;
    }

    public static byte readByte(InputStream inputStream) {
        byte[] b = new byte[1];
        try {
            final int read = inputStream.read(b);
            if (read <= 0) {
                throw new IllegalArgumentException("data is null");
            }
            return b[0];
        } catch (IOException e) {
                throw new IllegalArgumentException("data is null");
        }
    }
}
