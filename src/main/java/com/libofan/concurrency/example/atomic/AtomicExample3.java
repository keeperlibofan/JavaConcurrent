package com.libofan.concurrency.example.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicExample3 {
    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        atomicBoolean.compareAndSet(false, true);
    }
}
