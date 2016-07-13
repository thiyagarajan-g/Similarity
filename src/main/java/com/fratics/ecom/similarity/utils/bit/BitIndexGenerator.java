package com.fratics.ecom.similarity.utils.bit;

import static java.lang.Math.pow;

public class BitIndexGenerator {
    public static int generateBitSetIndex(String str) {
        char[] chars = str.toCharArray();
        int sum = 0;
        int k = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            sum = sum + (int) ((chars[i] - 'a') * pow(26, k));
            k++;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.err.println(generateBitSetIndex("aa"));
        System.err.println(generateBitSetIndex("ba"));
        System.err.println(generateBitSetIndex("zz"));
        System.err.println();
        System.err.println(generateBitSetIndex("aaa"));
        System.err.println(generateBitSetIndex("aba"));
        System.err.println(generateBitSetIndex("azz"));
    }
}
