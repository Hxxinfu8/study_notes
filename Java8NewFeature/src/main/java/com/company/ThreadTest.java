package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest implements Runnable {
    private AtomicInteger num;
    public ThreadTest(AtomicInteger num) {
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            System.out.println(num.incrementAndGet());
        }
    }
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Thread threadA= new Thread(new ThreadTest(atomicInteger));
        Thread threadB= new Thread(new ThreadTest(atomicInteger));
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
