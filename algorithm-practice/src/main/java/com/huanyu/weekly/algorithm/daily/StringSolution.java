package com.huanyu.weekly.algorithm.daily;

import java.util.ArrayList;
import java.util.List;

public class StringSolution {
    //
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int maxLen = 1;
        StringBuilder chStr = new StringBuilder();
        char[] chars = s.toCharArray();

        for (char aChar : chars) {
            if (chStr.toString().contains(aChar + "")) {
                chStr = new StringBuilder(chStr.substring(chStr.lastIndexOf(aChar + "")));
                if (chStr.length() > maxLen) {
                    maxLen = chStr.length();
                }

                System.out.println(chStr);
            }

            if (!chStr.toString().contains(aChar + "")) {
                chStr.append(aChar);
            }
            System.out.println(chStr);
        }

        if (maxLen < chStr.length()) {
            maxLen = chStr.length();
        }
        return maxLen;
    }

    public static void main(String[] args) {
        StringSolution stringSolution = new StringSolution();
        System.out.println(stringSolution.lengthOfLongestSubstring("bbbbb"));
    }
}
