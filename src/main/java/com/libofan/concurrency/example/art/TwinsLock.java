package com.libofan.concurrency.example.art;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// 自定义同步组件, 同一个时刻只能有两个线程同时访问，否则就会被阻塞
public class TwinsLock implements Lock {
    private static final class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException();
            }
            setState(count);
        }

        // 保证线程安全, 不可中断，也就是要疯狂自旋, 非公平锁
        @Override
        protected int tryAcquireShared(int reduceAcquire) {
            for (; ; ) {
                if (hasQueuedPredecessors()) {
                    return -1;
                }
                int avaliable = getState();
                int remaining = avaliable - reduceAcquire;
                if (remaining < 0 || compareAndSetState(avaliable, remaining)) {
                    return remaining;
                }
            }
        }

        // 保证线程安全，和tryAcquireShared搭配使用
        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int current = getState();
                int remaining = current + arg;
                if (compareAndSetState(current, remaining)) {
                    return true;
                }
            }
        }
    }

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public boolean tryLock() {
        return false;
    }
}