package com.libofan.concurrency.example.art;

import org.assertj.core.data.MapEntry;

import java.util.Map;
import java.util.concurrent.*;

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
            // fork如何实现, 调用fork发生了什么?
            int leftResult = leftCountTask.fork().join();
            int rightResult = rightCountTask.fork().join();
            sum += leftResult;
            sum += rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

        }
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        cyclicBarrier.reset();
        Exchanger<Integer> exchanger = new Exchanger<>();
        CountTask countTask = new CountTask(1, 4);
        try {
            System.out.println(forkJoinPool.submit(countTask).get());
        } catch (ExecutionException | InterruptedException e) {
        }
    }
}
