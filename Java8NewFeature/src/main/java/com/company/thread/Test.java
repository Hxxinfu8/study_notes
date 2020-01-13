package com.company.thread;

import java.util.concurrent.CountDownLatch;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Depot depot = new Depot(100);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        producer.product(10);
        consumer.consume(5);
        producer.product(100);
        consumer.consume(50);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        ZookeeperLock lockA = new ZookeeperLock("111.230.235.170:2181", "test");

        ZookeeperLock lockB = new ZookeeperLock("111.230.235.170:2181", "test");
        // lock.deleteNodes();
        TestRunnable runnableA = new TestRunnable(countDownLatch, lockA);

        TestRunnable runnableB = new TestRunnable(countDownLatch, lockB);
        Thread A = new Thread(runnableA);

        Thread B = new Thread(runnableB);

        A.start();
        B.start();
        countDownLatch.await();
    }

    static class TestRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        private ZookeeperLock lock;
        private ThreadLocal threadLocal = new ThreadLocal();

        public TestRunnable(CountDownLatch countDownLatch, ZookeeperLock lock) {
            this.countDownLatch = countDownLatch;
            this.lock = lock;
            threadLocal.set("1111");
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "  " + Thread.currentThread().getName());
                Thread.sleep(10000);
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
