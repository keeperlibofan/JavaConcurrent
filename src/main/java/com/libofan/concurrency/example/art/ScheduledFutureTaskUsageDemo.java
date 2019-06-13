package com.libofan.concurrency.example.art;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledFutureTaskUsageDemo<T> {
    private Object[] items;
    private final int DEFAULT_SIZE = 16;

    public ScheduledFutureTaskUsageDemo() {
        items = new Object[DEFAULT_SIZE];

    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(16);
        // 同步队列
        ArrayBlockingQueue<String> stringArrayBlockingQueue = new ArrayBlockingQueue<>(16, false);
        executor.shutdown();
    }
}
