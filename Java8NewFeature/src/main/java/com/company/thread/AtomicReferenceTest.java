package com.company.thread;

import com.company.Status;
import com.company.Task;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Task> atomicReference = new AtomicReference<>();
        atomicReference.set(new Task(Status.CLOSED, 1));
    }
}
