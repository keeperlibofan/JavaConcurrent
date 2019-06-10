package com.libofan.concurrency.example.art;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁，就是在同一时间只能有一个线程得到这个锁，而其他获取锁的线程
 * 只能处于等待状态，只有获取锁的线程释放了锁，后续的线程才能够获取当
 * 前锁, 通过一个同步器来了解其并发原理。
 */
public class Mutex implements Lock {
    private static class Sync extends AbstractQueuedSynchronizer {
        // 没有判断是否是当前线程释放同步锁
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private final Sync sync = new Sync();
    @Override
    public boolean tryLock() {
        return false;
    }

    // 锁住当前线程除非它被中断。
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }
}
