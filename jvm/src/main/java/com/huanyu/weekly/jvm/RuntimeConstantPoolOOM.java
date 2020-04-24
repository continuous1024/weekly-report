package com.huanyu.weekly.jvm;

public class RuntimeConstantPoolOOM {
    // intern()方法需要在常量池里记录一下首次出现的实例引用
    // 因此intern()返回的引用和由StringBuilder创建的那个字符串实例就是同一个。
    // 而对str2比较返回false，这是因为“java”这个字符串在执行StringBuilder.toString()之前就已经出现过了，
    // 字符串常量池中已经有它的引用，不符合intern()方法要求“首次遇到”的原则，
    // “计算机软件”这个字符串则是首次出现的，因此结果返回true。
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
