package com.libofan.concurrency.example.J.U.C;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JUCExample4 {
    public static CyclicBarrier c = new CyclicBarrier(2, () -> {

    });

    public static void main(String[] args) {
        new Thread(() -> {
            try {

            } catch (Exception e) {

            }
        }).start();
    }

    static class A implements Runnable {
        @Override
        public void run() {
             System.out.println(3);
        }
    }
}
