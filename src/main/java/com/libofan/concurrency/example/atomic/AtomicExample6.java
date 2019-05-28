package com.libofan.concurrency.example.atomic;


import java.util.concurrent.atomic.AtomicBoolean;


public class AtomicExample6 {
    private static AtomicBoolean isHappend = new AtomicBoolean(false);

    public static void main(String[] args) {

    }
    private void test() {
        if (isHappend.compareAndSet(false, true)) {
            System.out.println("execued");
        }
    }
}
