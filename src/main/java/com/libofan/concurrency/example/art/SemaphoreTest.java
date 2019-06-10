package com.libofan.concurrency.example.art;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTest {
    private final Semaphore semaphore = new Semaphore(10);
    private static ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();

    public static void main(String[] args) {
        SemaphoreTest st = new SemaphoreTest();
        ConcurrentHashMap<?, ?> concurrentHashMap1 = new ConcurrentHashMap<>();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    }

    private void count() {
        try {
            ReentrantLock reentrantLock = new ReentrantLock();
            reentrantLock.lock();
            semaphore.acquire();
            add(1);
            semaphore.release();
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
        } catch (Exception e) {

        }
    }

    private void add(int arg) {

    }
}
