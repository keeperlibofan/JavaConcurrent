package com.libofan.concurrency.example.J.U.C;


import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class JUCExample2 {
    final static ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

    public static void main(String[] args) {
        map.put("a", "b");
        Comparator<Integer> integerComparator;// 泛型接口

    }
}
