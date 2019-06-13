package com.libofan.concurrency.example.art;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveAction recursiveAction = new RecursiveAction() {
            @Override
            protected void compute() {

            }
        };
        ForkJoinTask<String> forkJoinTask = new ForkJoinTask<String>() {
            @Override
            public String getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(String value) {

            }

            @Override
            protected boolean exec() {
                return false;
            }
        };
        forkJoinPool.execute(forkJoinTask);
    }
}
