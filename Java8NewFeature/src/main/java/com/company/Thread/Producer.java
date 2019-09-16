package com.company.Thread;

public class Producer {
    private Depot depot;

    Producer (Depot depot) {
        this.depot = depot;
    }

    public void product(int value) {
        new Thread(() -> {
            depot.product(value);
        }).start();
    }
}
