package com.huanyu.weekly.algorithm.daily;

public class ShortConvert {
    // 10进制转化为任意进制
    public static String tenConvert(int a, String alphabets) {
        StringBuilder result = new StringBuilder();
        int length = alphabets.length();
        while (a != 0) {
            int b = a % length;
            a = a / length;
            char c = alphabets.charAt(b);
            result.insert(0, c);
        }
        String resultStr = result.toString();
        if ("".equals(resultStr)) {
            return String.valueOf(alphabets.charAt(0));
        }
        return resultStr;
    }

    public static void main(String[] args) {
        int a = 10086;
        String alphabets = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println(tenConvert(a, alphabets));
    }
}
