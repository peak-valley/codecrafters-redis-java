package com.zyf.rdb;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RdbUtil {
    // https://github.com/codecrafters-io/redis-tester/blob/main/internal/assets/empty_rdb_hex.md
    public static final String EMPTY_RDB_HEX = "524544495330303131fa0972656469732d76657205372e322e30fa0a72656469732d62697473c040fa056374696d65c26d08bc65fa08757365642d6d656dc2b0c41000fa08616f662d62617365c000fff06e3bfec0ff5aa2";
    public static final String MAGIC_NUMBER = "REDIS";

    public static List<Integer> openRdbFile(String dir, String fileName) throws IOException {
        var bytes = Files.readAllBytes(Paths.get(dir + "/" + fileName));
        var result = IntStream.range(0, bytes.length)
                .mapToObj(i -> Byte.toUnsignedInt(bytes[i]))
                .toList();
        if (!validateRdb(result)) {
            throw new IllegalArgumentException("redis failed - invalid rdb file");
        }
        System.out.println("rdb -> " + convertIntByteListToString(result));
        return result;
    }

    public static boolean validateRdb(List<Integer> bytes) {
        return MAGIC_NUMBER.equals(convertIntByteListToString(bytes.subList(0, 5)));
    }

    public static String convertIntByteListToString(List<Integer> bytes) {
        return bytes.stream()
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    public static List<Integer> stringconvertStringToIntByteList(String s) {
        byte[] bytes = s.getBytes(Charset.defaultCharset());
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> Byte.toUnsignedInt(bytes[i]))
                .toList();
    }
}
