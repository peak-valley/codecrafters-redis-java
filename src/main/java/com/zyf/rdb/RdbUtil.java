package com.zyf.rdb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
        System.out.println("rdb -> \n" + Arrays.toString(bytes));
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
        int REDIS_FC = 0xFC;
        byte[] bytes = new byte[] {82, 69, 68, 73, 83, 48, 48, 48, 51, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 1, 0, 0, 5, 109, 97, 110, 103, 111, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, -1, 56, 89, -71, 80, -91, 70, 23, 87, 10};
        byte[] bytes2 = new byte[] {
                82, 69, 68, 73, 83, 48, 48, 48, 51, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 1, 0, 0, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, -1, -88, -84, -19, 29, 66, 84, -36, -29, 10
        };
        byte[] bytes6 = new byte[] {
                82, 69, 68, 73, 83, 48, 48, 48, 51, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -2, 0, -5, 5, 5, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, 6, 98, 97, 110, 97, 110, 97, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 6, 111, 114, 97, 110, 103, 101, -4, 0, -100, -17, 18, 126, 1, 0, 0, 0, 6, 111, 114, 97, 110, 103, 101, 5, 109, 97, 110, 103, 111, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 4, 112, 101, 97, 114, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 9, 112, 105, 110, 101, 97, 112, 112, 108, 101, 9, 112, 105, 110, 101, 97, 112, 112, 108, 101, -1, 40, -8, 7, 26, 75, -125, -10, -33, 10
        };

        OutputStream os = new FileOutputStream(new File("F:\\code\\oneself\\codecrafters\\codecrafters-redis-java\\dump6.rdb"));
        os.write(bytes6);


        List<String> list = IntStream.range(0, bytes.length).mapToObj(i -> Integer.toHexString(bytes[i])).toList();
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
