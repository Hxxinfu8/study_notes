package com.company.Thread;

public class Depot {
    private int capacity;
    private int has;

    Depot(int capacity) {
        this.capacity = capacity;
        this.has = 0;
    }

    public synchronized void product(int value) {
        try {
            int left = value;
            while (left > 0) {
                while (has >= capacity) wait();
                int inc = left + has > capacity ? capacity - has : left;
                has += inc;
                left -= inc;
                System.out.printf("%s produce %d, inc=%d, has=%d\n", Thread.currentThread().getName(), value, inc, has);
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void consume(int value) {
        try {
            int left = value;
            while (left > 0) {
                while (has <= 0) wait();
                int dec = value > has ? has : value;
                has -= dec;
                left -= dec;
                System.out.printf("%s consume %d, dec=%d, has=%d\n", Thread.currentThread().getName(), value, dec, has);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
