package com.libofan.concurrency.example.commonUnsafe;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DataFormatExample1 {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMDD");
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Class<?> a = list.getClass();
        System.out.println(a.getName());
    }
}
