package com.huanyu.weekly.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportStudy {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin park");
        // 使当前线程获取许可证
        Thread child = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
        });
        child.start();
        TimeUnit.SECONDS.sleep(1);
        LockSupport.unpark(child);
        System.out.println("end park");
        child.interrupt();
    }
}
