package com.libofan.concurrency.example.art;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    final Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        SemaphoreTest st = new SemaphoreTest();

    }

    private void count() {
        try {
            semaphore.availablePermits();
            semaphore.tryAcquire();
            semaphore.hasQueuedThreads();
            semaphore.getQueueLength();
            semaphore.acquire();
        } catch (Exception e) {
        }
    }
}
