package com.libofan.concurrency.example.art;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExchangerTest {
    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ThreadPoolExecutor executor;
    public static void main(String[] args) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        executor.prestartAllCoreThreads();

    }
}
