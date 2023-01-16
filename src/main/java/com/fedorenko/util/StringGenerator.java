package com.fedorenko.util;

import java.util.Random;

public class StringGenerator {
    private final static Random RANDOM = new Random();
    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String randomString() {
        StringBuilder result = new StringBuilder();
        int[] randomArr = generateArray();
        for (int indexOfChar : randomArr) {
            boolean isUpperCase = RANDOM.nextBoolean();
            if (isUpperCase) {
                result.append(String.valueOf(ALPHABET.charAt(indexOfChar)).toUpperCase());
            } else {
                result.append(ALPHABET.charAt(indexOfChar));
            }
        }
        return result.toString();
    }
    private static int[] generateArray() {
        final int minSizeOfArr = 3;
        final int maxSizeOfArr = 10;
        return RANDOM.ints(RANDOM.ints(1, minSizeOfArr, maxSizeOfArr)
                .findAny().getAsInt(), 0, ALPHABET.length() - 1).toArray();
    }
}
