package com.company.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Depot {
    private int capacity;
    private int has;
    private Lock lock;
    private Condition emptyCondition;
    private Condition fullCondition;

    Depot(int capacity) {
        this.capacity = capacity;
        this.has = 0;
        this.lock = new ReentrantLock();
        this.emptyCondition = lock.newCondition();
        this.fullCondition = lock.newCondition();
    }

    public void product(int value) {
        lock.lock();
        try {
            int left = value;
            while (left > 0) {
                while (has >= capacity) fullCondition.await();
                int inc = left + has > capacity ? capacity - has : left;
                has += inc;
                left -= inc;
                System.out.printf("%s produce %d, inc=%d, has=%d\n", Thread.currentThread().getName(), value, inc, has);
                emptyCondition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int value) {
        lock.lock();
        try {
            int left = value;
            while (left > 0) {
                while (has <= 0) emptyCondition.wait();
                int dec = value > has ? has : value;
                has -= dec;
                left -= dec;
                System.out.printf("%s consume %d, dec=%d, has=%d\n", Thread.currentThread().getName(), value, dec, has);
                fullCondition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
