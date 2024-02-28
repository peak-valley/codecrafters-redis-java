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
        System.out.println("rdb -> \n" + IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList());
        System.out.println("rdb -> \n" + Arrays.toString(bytes));
        System.out.println(result);
        return result;
    }

    public static boolean validateRdb(List<Integer> bytes) {
        return MAGIC_NUMBER.equals(convertIntByteListToString(bytes.subList(0, 5)));
    }

    public static void main(String[] args) {
        /**
//        String rdb = "524544495330303033fffffffa972656469732d7665725372e322e30fffffffaa72656469732d62697473ffffffc040fffffffe0fffffffb10056d616e676fa73747261776265727279ffffffff78ffffffa0fffffff6ffffffafffffff80ffffffb14a7da";
         * 6进制
         * [your_program] [52, 45, 44, 49, 53, 30, 30, 30, 33, fffffffa, 9, 72, 65, 64, 69, 73, 2d, 76, 65, 72, 5, 37, 2e, 32, 2e, 30, fffffffa, a, 72, 65, 64, 69, 73, 2d, 62, 69, 74, 73, ffffffc0, 40, fffffffe, 0, fffffffb, 1, 0, 0, 4, 70, 65, 61, 72, 5, 61, 70, 70, 6c, 65, ffffffff, 62, d, 3b, 35, ffffffb3, 41, ffffffe4, ffffffb0, a]
         * [your_program] [82, 69, 68, 73, 83, 48, 48, 48, 51, 250, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, 250, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, 192, 64, 254, 0, 251, 1, 0, 0, 4, 112, 101, 97, 114, 5, 97, 112, 112, 108, 101, 255, 98, 13, 59, 53, 179, 65, 228, 176, 10]
         */
        byte[] bytes = new byte[] {82, 69, 68, 73, 83, 48, 48, 48, 51, 250, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, 250, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, 192, 64, 254, 0, 251, 1, 0, 0, 4, 112, 101, 97, 114, 5, 97, 112, 112, 108, 101, 255, 98, 13, 59, 53, 179, 65, 228, 176, 10};
//        String s = IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList();
        List<String> list = IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList();
        System.out.println(list);
//        System.out.println(s);

//        System.out.println(list.stream().mapTo(i -> Integer.parseInt(Integer.toHexString(list.get(i)))).to);
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
