package com.libofan.concurrency.example.art;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyReentrantReadWriteLock {
    private final WriteLock writerLock;
    private final Sync sync;

    abstract static class Sync extends AbstractQueuedSynchronizer {
        private transient Thread firstReader = null;

        static final int SHARED_SHIFT   = 16;
        static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
        static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
        static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

        /** Returns the number of shared holds represented in count  */
        static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
        /** Returns the number of exclusive holds represented in count  */
        static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

        abstract boolean writerShouldBlock();

        // 获取写锁, 会设置独占线程
        @Override
        protected boolean tryAcquire(int acquires) {
            int c = getState();
            int w = exclusiveCount(c);
            if (c != 0) {
                if (w == 0 || getExclusiveOwnerThread() != Thread.currentThread()) {
                    return false;
                }
                if (w + exclusiveCount(acquires) > MAX_COUNT) {
                    throw new Error("Mac count access");
                }
                setState(c + acquires);
                return true;
            }
            if (writerShouldBlock() || !compareAndSetState(c, c + acquires)) {
                return false;
            }
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }

        // 共享锁的获取接口
        @Override
        protected int tryAcquireShared(int arg) {
            return -1;
        }
    }

    static final class FairSync extends Sync {
        @Override
        boolean writerShouldBlock() {
            return hasQueuedPredecessors();
        }
    }

    static final class NonfairSync extends Sync {
        @Override
        boolean writerShouldBlock() {
            return false;
        }
    }

    public static class WriteLock implements Lock {
        private final Sync sync;

        protected WriteLock(MyReentrantReadWriteLock lock) {
            sync = lock.sync;
        }

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public void unlock() {

        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    public MyReentrantReadWriteLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
        writerLock = new WriteLock(this);

    }

    public static class ReadLock implements Lock {
        @Override
        public void lock() {

        }

        @Override
        public void unlock() {

        }

        @Override
        public Condition newCondition() {
            return null;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }
    }

    public WriteLock writeLock() { return writerLock; }
}
