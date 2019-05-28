package com.libofan.concurrency.example.SingletonExample;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicStampedReference;

public class SingletonExample1 {

    static {

    }
    // 这段代码我们只让执行一次
    private SingletonExample1() {
    }

    private static AtomicBoolean isInstanced = new AtomicBoolean(false);
    // 解决ABA问题, 其实在这个例子中不存在ABA问题
    private static AtomicStampedReference<Boolean> noABAIsInstanced = new AtomicStampedReference<>(false, 0);

    private static SingletonExample1 instance;

    public SingletonExample1 getInstance() {
        if (noABAIsInstanced.compareAndSet(false, true, 0, 1)) {
            instance = new SingletonExample1();
        }
        return instance;
    }
}
