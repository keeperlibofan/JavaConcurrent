package com.libofan.concurrency.example.J.U.C;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.*;

@Slf4j
public class JUCExample1{

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    private final static CountDownLatch countDownLatch = new CountDownLatch(3);

    private final static CyclicBarrier cb = new CyclicBarrier(10);

    public static void main(String[] args) {
        list.add("1");
        list.add("2");
        list.add("3");
        Semaphore semaphore = new Semaphore(10);
        semaphore.tryAcquire();
        cb.reset();

        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<String> iter = list.iterator();

        // 存放10个线程的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        // 执行10个任务(我当前正在迭代集合（这里模拟并发中读取某一list的场景）)
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                    while (iter.hasNext()) {
                        System.err.println(iter.next());
                    }
            });
        }

        // 执行10个任务
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                    list.add("121");// 添加数据
            });
        }

        System.err.println(Arrays.toString(list.toArray()));
    }
}

