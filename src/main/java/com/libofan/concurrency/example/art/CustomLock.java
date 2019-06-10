package com.libofan.concurrency.example.art;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

// 自定义公平锁
public class CustomLock extends AbstractQueuedSynchronizer {
    // 独占式:获取同步锁
    protected final boolean tryAcquire(int arg) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) { // 当前可以获取这个锁
            if (!hasQueuedPredecessors()
                    && compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        } else if (getExclusiveOwnerThread() == current) { // c 大于0 的情况下，这个时候是可以重入的
            int nextc = c + arg;
            if (nextc < 0) {
                throw new Error("Maximum lock count exceeded");
            }
            // 为什么这里转换不用CAS是因为在这个线程中只可能是同样的线程重入进入了这个代码块，
            // 是不可能有同时有第二个线程进入此代码区域的，所以这里不必用CAS
            setState(nextc);
            return true;
        }
        return false;
    }

    // 独占式:返回当前线程是否被释放
    protected final boolean tryRelease(int release) {
        int c = getState() - release;
        if (Thread.currentThread() != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
        }
        boolean free = false;
        if (c == 0) { // 如果当前锁已经被释放
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }

    // 共享模式：公平锁，会循环阻塞
    protected final int tryAcquireShared(int acquires) {
        for (; ; ) {
            if (hasQueuedPredecessors()) {
                return -1;
            }
            int current = getState();
            int remaining = current - acquires;
            if (remaining < 0 ||
                    compareAndSetState(current, remaining)) {
                return remaining;
            }
        }
    }

    protected final boolean tryReleaseShared(int releases) {
        for (;;) {
            int current = getState();
            int next = current + releases;
            if (current < next) { // overflow
                throw new Error("Maximum permit count exceeded");
            }
            if (compareAndSetState(current, next)) {
                return true;
            }
        }
    }

}
