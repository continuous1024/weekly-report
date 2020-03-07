package com.huanyu.weekly.algorithm.daily;

import java.util.Arrays;

/**
 * 指定大小
 */
public class RandomAndNotRepeat {
    public static int[] genRandomAndNotRepeatArray(int genCount, int size) {
        int[] result = new int[genCount];
        if (genCount > size) {
            return null;
        }
        for (int i = 0; i < genCount; i++) {
            int genIndex = (int) (Math.random() * size + 1);
            result[i] = genIndex;
            for (int j = 0; j < i; j++) {
                if (result[j] == result[i]) {
                    i--;
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        int[] result = genRandomAndNotRepeatArray(20, 19);
        Arrays.sort(result);
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < result.length; i ++) {
            System.out.println(result[i]);
        }
    }
}
