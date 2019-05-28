package com.libofan.concurrency.example.atomic;


import lombok.Getter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicExample5 {
    private final static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    @Getter
    private volatile int count = 100;

    public static void main(String[] args) {
        AtomicExample5 atomicExample5 = new AtomicExample5();

        updater.compareAndSet(atomicExample5, 100, 120);
        System.out.printf("count: %d", atomicExample5.count);
    }
}