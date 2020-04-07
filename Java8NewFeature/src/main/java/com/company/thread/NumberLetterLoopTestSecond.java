package com.company.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Upoint0002
 */
public class NumberLetterLoopTestSecond {
    private static Thread t1 , t2 = null;
    private static Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static String letters = "ABCDEFGHIJKLMNOP";

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            try {
                for (Integer num : nums) {
                    System.out.print(num);
                    LockSupport.unpark(t2);
                    LockSupport.park(t1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1");
        t2 = new Thread(() -> {
            try {
                for (char letter : letters.toCharArray()) {
                    LockSupport.park(t2);
                    System.out.print(letter);
                    LockSupport.unpark(t1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
