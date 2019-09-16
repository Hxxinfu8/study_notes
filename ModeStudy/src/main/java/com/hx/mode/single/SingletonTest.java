package com.hx.mode.single;

import java.util.concurrent.CountDownLatch;

public class SingletonTest {
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) {

        Runnable runnable = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Singleton singleton = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + ": " + singleton.hashCode());
        };

        Thread threadA = new Thread(runnable, "A");
        Thread threadB = new Thread(runnable, "B");
        latch.countDown();
        threadA.start();
        threadB.start();
    }
}
