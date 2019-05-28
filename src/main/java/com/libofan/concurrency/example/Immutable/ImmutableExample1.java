package com.libofan.concurrency.example.Immutable;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class ImmutableExample1 {
     private final static Integer a = 1;
     private final static String b = "2";
     private static Map<Integer, Integer> map = Maps.newHashMap();

     public static void main(String[] args) {
          map.put(3, 4);
          map.put(5, 6);
          map = Collections.unmodifiableMap(map);
     }
}
