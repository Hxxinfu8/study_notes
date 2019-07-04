package com.company;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(() -> System.out.println("111"),2, 10, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(() -> System.out.println("222"),2, 10, TimeUnit.SECONDS);
    }
}
