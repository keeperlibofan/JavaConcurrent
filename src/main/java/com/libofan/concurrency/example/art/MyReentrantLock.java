package com.libofan.concurrency.example.art;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyReentrantLock implements Lock {
    private final Sync sync;
    //
    static abstract class Sync extends AbstractQueuedSynchronizer {
        final boolean nonfairTryAcquire(int acquire) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquire)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquire;
                if (nextc < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            return false;
        }

        abstract void lock();

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            // 判断当前线程是否是独占线程
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }

    // 不写的时候就是friendly, 同一package的就可以用
    static final class NofairSync extends Sync {
        protected final boolean tryAcquire(int arg) {
            return nonfairTryAcquire(arg);
        }

        @Override
        final void lock() {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                acquire(1);
            }
        }
    }

    static final class FairSync extends Sync {
        // 公平version要判断当前线程在队列中是否是头部线程
        @Override
        protected boolean tryAcquire(int acquire) {
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquire)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            else if (getExclusiveOwnerThread() == Thread.currentThread()) { // 独占的
                int nextc = getState() + acquire;
                if (nextc < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            return false;
        }

        @Override
        void lock() {

        }
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock() {
        return false;
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
    public void unlock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    public MyReentrantLock() {
        sync = new NofairSync();
    }

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        // 默认是不公平锁
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        Condition condition = writeLock.newCondition();
        condition.awaitUninterruptibly();
        readWriteLock.getReadLockCount();
    }
}
