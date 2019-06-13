package com.libofan.concurrency.example.art;

import javax.validation.constraints.NotNull;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayBlockingQueue<T> extends AbstractQueue<T>
        implements BlockingQueue<T>, java.io.Serializable {
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;

    private int count; // 会自动初始化

    private Object[] items;

    private int putIndex;

    private int takeIndex;

    transient int irts;

    public MyArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(@NotNull T t) throws InterruptedException {
        checkNotNull(t);
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                notFull.await();
            }
            enqueue(t);
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(Object t) {
        items[putIndex] = t;
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
        notEmpty.signal();
    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {

        return false;
    }

    @Override
    public T take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    private T dequeue() {
        T result = (T)items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        notFull.signal();
        return result;
    }


    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    public static void main(String[] args) {
        Runnable[] runnables = new Runnable[10];
        runnables[0].run();
        ForkJoinPool forkJoinPool = new ForkJoinPool();

    }

    private void checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
    }
}
