package com.company.Thread;

public class Test {
    public static void main(String[] args) {
        Depot depot = new Depot(100);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        producer.product(10);
        consumer.consume(5);
        producer.product(100);
        consumer.consume(50);
    }
}
