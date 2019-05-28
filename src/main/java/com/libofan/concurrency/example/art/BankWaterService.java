package com.libofan.concurrency.example.art;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;

public class BankWaterService implements Runnable{
    private CyclicBarrier c = new CyclicBarrier(4, this);

    private Executor executor = Executors.newFixedThreadPool(4);

    private ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                concurrentHashMap.put(Thread.currentThread().getName(), 1);
                try {
                   c.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
        bankWaterService.c.reset();
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : concurrentHashMap.entrySet()) {
            result += entry.getValue();
        }
        System.out.println(result);
    }
}
