package com.zyf.rdb;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        return result;
    }

    public static boolean validateRdb(List<Integer> bytes) {
        return MAGIC_NUMBER.equals(convertIntByteListToString(bytes.subList(0, 5)));
    }

    public static final Integer REDIS_RDB_SELECTEB = 0xFE;
    public static final Integer REDIS_RDB_EXPIRETIME = 0xFD;
    public static final Integer REDIS_RDB_EXPIRETIMEMS = 0xFC;
    public static final Integer REDIS_RDB_RESIZEDB = 0xFB;
    public static final Integer REDIS_RDB_AUX = 0xFA;

    public static void main(String[] args) throws IOException {
        /**
//        String rdb = "524544495330303033fffffffa972656469732d7665725372e322e30fffffffaa72656469732d62697473ffffffc040fffffffe0fffffffb10056d616e676fa73747261776265727279ffffffff78ffffffa0fffffff6ffffffafffffff80ffffffb14a7da";
         * 6进制
         * your_program] [52, 45, 44, 49, 53, 30, 30, 30, 33, fffffffa, 9, 72, 65, 64, 69, 73, 2d, 76, 65, 72, 5, 37, 2e, 32, 2e, 30, fffffffa, a, 72, 65, 64, 69, 73, 2d, 62, 69, 74, 73, ffffffc0, 40, fffffffe, 0, fffffffb, 1, 0, 0, 5, 6d, 61, 6e, 67, 6f, 9, 72, 61, 73, 70, 62, 65, 72, 72, 79, ffffffff, 38, 59, ffffffb9, 50, ffffffa5, 46, 17, 57, a]
         * [your_program] rdb ->
         * [your_program] [82, 69, 68, 73, 83, 48, 48, 48, 51, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 1, 0, 0, 5, 109, 97, 110, 103, 111, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, -1, 56, 89, -71, 80, -91, 70, 23, 87, 10]
         * [your_program] [82, 69, 68, 73, 83, 48, 48, 48, 51, 250, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, 250, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, 192, 64, 254, 0, 251, 1, 0, 0, 5, 109, 97, 110, 103, 111, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 255, 56, 89, 185, 80, 165, 70, 23, 87, 10]
         */
        int REDIS_FC = 0xFC;
        byte[] bytes = new byte[] {82, 69, 68, 73, 83, 48, 48, 48, 51, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 1, 0, 0, 5, 109, 97, 110, 103, 111, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, -1, 56, 89, -71, 80, -91, 70, 23, 87, 10};
        OutputStream os = new FileOutputStream(new File("F:\\code\\oneself\\codecrafters\\codecrafters-redis-java\\dump.rdb"));
//        os.write(bytes);


        List<String> list = IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList();
        List<Integer> list1 = IntStream.range(0, bytes.length).mapToObj(i -> Byte.toUnsignedInt(bytes[i])).toList();
        List<Integer> list2 = IntStream.range(0, bytes.length)
                .mapToObj(i -> Byte.toUnsignedInt(bytes[i]))
                .toList();
//        List<String> list = IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList();
        System.out.println(list);
        System.out.println(list2);
        for (int j = 0; j < list2.size(); j++) {
            Integer i = list2.get(j);
            if (Objects.equals(i, REDIS_RDB_SELECTEB)) {
                System.out.println(j + " REDIS_RDB_SELECTEB " + i);
                j++;
                var dbNumber = list2.get(j);
                System.out.println(dbNumber);

                if (!Objects.equals(list2.get(j + 1), REDIS_RDB_RESIZEDB)) {
                    break;
                }
                // j++ 为 REDIS_RDB_RESIZEDB，所以需要再加一个
                j += 2;
                System.out.println(list.get(j));
            }
        }

//        List<Integer> integers = list2.subList(9, 26);
//        System.out.println(convertIntByteListToString(integers));
//        integers = list2.subList(26, 40);
//        System.out.println(convertIntByteListToString(integers));
//        integers = list2.subList(40, 42);
//        System.out.println(convertIntByteListToString(integers));
        /**
         * byte 正常范围为 -128 ~127 一共256个数（其中包括 0）
         */
        byte k = (byte) 0;
        System.out.println(k);
//        for (Integer i : list2) {
//            if (Objects.equals(i, REDIS_RDB_EXPIRETIME)) {
//                System.out.println("REDIS_RDB_EXPIRETIME " + i);
//            } else if (Objects.equals(i, REDIS_RDB_SELECTEB)) {
//                System.out.println("REDIS_RDB_SELECTEB " + i);
//            } else if (Objects.equals(i, REDIS_RDB_EXPIRETIMEMS)) {
//                System.out.println("REDIS_RDB_EXPIRETIMEMS " + i);
//            } else if (Objects.equals(i, REDIS_RDB_RESIZEDB)) {
//                System.out.println("REDIS_RDB_RESIZEDB " + i);
//            } else if (Objects.equals(i, REDIS_RDB_AUX)) {
//                System.out.println("REDIS_RDB_AUX " + i);
//            }
//        }
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
