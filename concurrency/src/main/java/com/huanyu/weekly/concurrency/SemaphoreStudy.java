package com.huanyu.weekly.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreStudy {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            System.out.println("[线程1]线程1 释放许可, 线程2 才能获取到许可执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        });

        executor.execute(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[线程2]线程2 获取到许可执行");
        });
    }
}
