package com.libofan.concurrency.example.art;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private int start;

    private int end;

    private static final int THRESHOLD = 2;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start <= THRESHOLD) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int mid = (start + end) / 2;
            CountTask leftCountTask = new CountTask(start, mid);
            CountTask rightCountTask = new CountTask(mid+1, end);
            int leftResult = leftCountTask.fork().join(); // 不用compute的原因就是提供一个异步的计算方案, fork如何实现
            int rightResult = rightCountTask.fork().join();
            sum += leftResult;
            sum += rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 4);
        try {
            System.out.println(forkJoinPool.submit(countTask).get());
        } catch (ExecutionException | InterruptedException e) {

        }
    }
}
