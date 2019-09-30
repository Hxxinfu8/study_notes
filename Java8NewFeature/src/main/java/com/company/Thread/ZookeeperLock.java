package com.company.Thread;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class ZookeeperLock implements Lock, Watcher {

    private ZooKeeper zooKeeper;

    private String lockName;

    private String waitNode; // 等待前一个锁

    private CountDownLatch lock; // 计数器

    private String myLock; // 当前锁

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String ROOT = "/locks";

    private int sessionTimeout = 2000;

    public ZookeeperLock (String config, String lockName) {
        this.lockName = lockName;
        try {
            zooKeeper = new ZooKeeper(config, sessionTimeout, this);
            countDownLatch.await();
            // 判断根节点是否存在
            Stat stat = zooKeeper.exists(ROOT, false);
            if (stat == null) {
                // 创建完全开放的ACL，任何连接的客户端都可以操作该属性的持久zNode
                zooKeeper.create(ROOT, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        if (this.tryLock()) {
            System.out.println("Thread: " + Thread.currentThread().getName() + "----" + this.myLock + " get lock true");
            return;
        }

        try {
            waitForLock(this.waitNode, sessionTimeout);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    @Override
    public boolean tryLock() {
        String split = "_lock_";
        if (this.lockName.contains(split)) throw new RuntimeException("创建临时结点失败");
        try {
            // 创建临时子节点
            this.myLock = zooKeeper.create(ROOT + "/" + this.lockName + split, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("临时结点" + this.myLock + "创建");

            List<String> children = zooKeeper.getChildren(ROOT, false);
            List<String> lockNodes = children.stream().filter(item -> item.split(split)[0].equals(this.lockName)).sorted().collect(Collectors.toList());

            // 判断是否为首结点
            if (myLock.equals(ROOT + "/" + lockNodes.get(0))) {
                System.out.println(myLock + "==" + lockNodes.get(0));
                return true;
            }

            String subNode = myLock.substring(myLock.lastIndexOf("/") + 1);
            waitNode = lockNodes.get(Collections.binarySearch(lockNodes, subNode) - 1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (this.tryLock()) {
            System.out.println("Thread: " + Thread.currentThread().getName() + "----" + this.myLock + " get lock true");
            return true;
        }

        try {
            waitForLock(this.waitNode, time);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            zooKeeper.delete(myLock, -1);
            myLock = null;
            zooKeeper.close();
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 监听有客户端连接
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            return;
        }

        if (this.lock != null) {
            this.lock.countDown();
        }
    }

    private Boolean waitForLock(String lower, long waitTime) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(ROOT + "/" + lower, true);
        if (stat != null) {
            System.out.println("Thread: " + Thread.currentThread().getName() + " wait for " + lower);
            this.lock = new CountDownLatch(1);
            this.lock.await(waitTime, TimeUnit.MILLISECONDS);
            this.lock = null;
        }

        return true;
    }

    public void deleteNodes() {
        try {
            List<String> children = zooKeeper.getChildren(ROOT, false);
            children.forEach(item -> {
                try {
                    System.out.println(item);
                    zooKeeper.delete(item, -1);
                } catch (InterruptedException | KeeperException e) {
                    e.printStackTrace();
                }
            });
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZookeeperLock lock = new ZookeeperLock("111.230.235.170:2181", "test");
        lock.deleteNodes();
    }
}
