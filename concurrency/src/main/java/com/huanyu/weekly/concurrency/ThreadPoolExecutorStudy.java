package com.huanyu.weekly.concurrency;

import java.util.concurrent.*;

public class ThreadPoolExecutorStudy {
    // Future Test
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        1,
                        1,
                        1L, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(1),
                        new ThreadPoolExecutor.DiscardPolicy()
                );

        Future futureOne = threadPoolExecutor.submit(() -> {
            System.out.println("Start runnable one");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Future futureTwo = threadPoolExecutor.submit(() -> {
            System.out.println("Start runnable Two");
        });

        Future futureThree = threadPoolExecutor.submit(() -> {
            System.out.println("Start runnable Three");
        });

        System.out.println("one: " + futureOne.get());
        System.out.println("two: " + futureTwo.get());
        System.out.println("three: " + futureThree.get());
    }
}
