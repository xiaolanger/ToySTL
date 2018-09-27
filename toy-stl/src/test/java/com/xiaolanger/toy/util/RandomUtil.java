package com.xiaolanger.toy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static Random RANDOM = new Random(System.currentTimeMillis());
    private static ArrayList<Character> TABLE = new ArrayList<Character>() {
        {
            for (char c = 65; c < 97 + 26; c++) {
                add(c);
            }
        }
    };

    private static String genRandomString() {
        int size = TABLE.size();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(TABLE.get(RANDOM.nextInt(size)));
        }

        return sb.toString();
    }

    public static List<String> genRandomStringList(int length) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(genRandomString());
        }
        return list;
    }

    public static List<Integer> genRandomIntList(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(RANDOM.nextInt(length));
        }
        return list;
    }

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static void main(String[] args) {
        System.out.println(TABLE);
        System.out.println(genRandomStringList(10));
        System.out.println(nextInt(100));
    }
}
