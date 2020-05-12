package com.huanyu.weekly.collection;

public class TypeLearn {
    public static void main(String[] args) {
    	Integer a = 1;
    	Integer b = 2;
    	Integer c = 3;
    	Integer d = 3;
    	Integer e = 321;
    	Integer f = 321;
    	Long g = 3L;
    	
    	System.out.println(c == d); // true  -128到127有缓存
    	System.out.println(e == f); // false
    	System.out.println(c == (a + b)); // true -128到127有缓存
    	System.out.println(c.equals(a + b)); // true  同类型且值相等
    	System.out.println(g == (a + b)); // true  -128到127有缓存
    	System.out.println(g.equals(a + b)); // false  类型不对
    }
}
