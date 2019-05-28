package com.libofan.concurrency.example.sync;

public class SynchronizedExample2 {
    public static int count;

    public static void test2(int j) {
        synchronized (SynchronizedExample2.class) { // 修饰的是这个静态方法
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        }
    }
}
