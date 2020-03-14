package com.huanyu.weekly.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CyclicBarrierStudy {
    // 每个任务有三个阶段
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            public void run() {
                System.out.println("all thread has cyclic");
            }
        });
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(new Work("FIRST", cyclicBarrier));
        executor.execute(new Work("SECOND", cyclicBarrier));
    }

    public static class Work implements Runnable {

        private String name;
        private CyclicBarrier cyclicBarrier;

        public Work(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                System.out.println(name + " start finish one");
                cyclicBarrier.await();
                System.out.println(name + " start finish two");
                cyclicBarrier.await();
                System.out.println(name + " start finish three");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
