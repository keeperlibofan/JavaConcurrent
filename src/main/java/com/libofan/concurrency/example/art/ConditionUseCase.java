package com.libofan.concurrency.example.art;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionUseCase {
    Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();

    Condition condition2 = lock.newCondition();


    // throw interruptedException就代表可被中断的
    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            condition1.await();
        } finally {
            lock.unlock();
        }
    }

    // 可被中断的
    public void conditionSignal() throws InterruptedException {
        lock.lock();
        try {
            condition2.await();
        } finally {
            lock.unlock();
        }
    }
}