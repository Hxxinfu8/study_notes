package org.hx.netty.study.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author hx
 */
public class AtomicUpdaterTest {
    public static void main(String[] args) {
        Person person = new Person();
        AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(updater.addAndGet(person, 1));
            });
            thread.start();
        }
    }
    static class Person {
        volatile int age = 1;
    }
}
