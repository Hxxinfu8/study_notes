package com.company.thread;

public class Solution {
    private int n;
    private volatile boolean flag = false;

    public Solution(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (flag) {
                Thread.yield();
            }
            printFoo.run();
            flag = true;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!flag) {
                Thread.yield();
            }
            printBar.run();
            flag = false;
        }
    }
}
