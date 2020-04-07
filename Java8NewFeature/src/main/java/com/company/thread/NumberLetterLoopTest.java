package com.company.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Upoint0002
 */
public class NumberLetterLoopTest {
    private static Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static String letters = "ABCDEFGHIJKLMNOP";
    private static Lock lock = new ReentrantLock();
    private static Condition conditionOne = lock.newCondition();
    private static Condition conditionTwo = lock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (Integer num : nums) {
                    System.out.print(num);
                    conditionTwo.signal();
                    conditionOne.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                for (char letter : letters.toCharArray()) {
                    System.out.print(letter);
                    conditionOne.signal();
                    conditionTwo.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
