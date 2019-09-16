package com.company.Thread;

public class Consumer {
    private Depot depot;
    Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(int value) {
        new Thread(() -> {
            depot.consume(value);
        }).start();
    }
}
