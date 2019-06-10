package com.libofan.concurrency.example.art;


import java.util.Hashtable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// 实现有界队列 java 的 channel, 最好能变成迭代器, 当一方面关闭的时候另外一方面也要关闭
public class BoundedQueued<T> {
    private Object[] items;
    // 代表队列中有几个元素,最大值就是items.length
    private int count;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    BoundedQueued(int size) {
        items = new Object[size];
    }

    public void add(T item) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[count++] = item;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T returnValue = (T)items[--count];
            notFull.signal();
            return returnValue;
        } finally {
            lock.unlock();
        }
    }
}
