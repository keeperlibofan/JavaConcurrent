package com.libofan.concurrency.example.art;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

// 自定义公平锁
public class CustomLock extends AbstractQueuedSynchronizer {
    protected final boolean tryAcquire(int arg) {
        final Thread current = Thread.currentThread();
        int curState = getState();
        if (curState == 0 ) { // 当前可以获取这个锁
            if (!hasQueuedPredecessors()) {

            }
        }
    }

}
