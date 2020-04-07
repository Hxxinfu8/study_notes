package com.company.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AQS extends AbstractQueuedSynchronizer {
    AtomicBoolean flag = new AtomicBoolean(true);

    @Override
    protected boolean tryAcquire(int arg) {
        if (compareAndSetState(0, 1)) {

        }
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }
}
