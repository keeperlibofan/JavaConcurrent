package com.libofan.concurrency.example.art;


import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledFutureTaskUsageDemo {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(16);
        executor.schedule();
        executor.shutdown();
    }
}
