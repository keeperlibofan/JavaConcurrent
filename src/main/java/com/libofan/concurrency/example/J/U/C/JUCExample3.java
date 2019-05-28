package com.libofan.concurrency.example.J.U.C;


import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JUCExample3 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        // 创建CyclicBarrier对象并设置3个公共屏障点
        final CyclicBarrier cb = new CyclicBarrier(3);
        for(int i = 0; i < 1; i++){
            Runnable runnable = () -> {
                    try {
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                        // 我这里只等待2000毫秒 两秒时间
                        cb.await(2000, TimeUnit.MILLISECONDS);//到此如果没有达到公共屏障点，则该线程处于等待状态，如果达到公共屏障点则所有处于等待的线程都继续往下运行

                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                        cb.await(2000, TimeUnit.MILLISECONDS);
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                        cb.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}
