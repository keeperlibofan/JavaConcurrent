package com.libofan.concurrency.example.SingletonExample;

// 对象被创建的三个过程
// 1 堆中分配内存
// 2 内存中初始化
// 3 线程中的变量指向该引用

// JVM和CPU优化后的顺序变成了1, 3, 2

public class SingletonExample2 {
    private SingletonExample2() {
    }

    // 单例作用 volatile + 双重检测机制 -> 禁止指令重拍
    private  volatile static SingletonExample2 instance;

    public SingletonExample2 getInstance() {
        if (instance == null)  {
            synchronized (SingletonExample2.class) {
                if (instance == null) {
                    instance = new SingletonExample2();
                }
            }
        }
        return instance;
    }
}
