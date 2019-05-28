package com.libofan.concurrency.example.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicExample4 {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);
    public static void main(String[] args) {
        atomicReference.compareAndSet(0, 2);
        atomicReference.compareAndSet(0, 1);
        atomicReference.compareAndSet(0, 3);
        System.out.printf("count: %d", atomicReference.get());
    }
}
