package zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ServerRegistry {
    private static CountDownLatch latch = new CountDownLatch(0);
    private static Stat stat = new Stat();
    private String uri;
    private ZooKeeper zooKeeper = null;

    public ServerRegistry(String uri) {
        this.uri = uri;
    }

    private ZooKeeper coonectServer() {
        try {
            zooKeeper = new ZooKeeper(uri, 5000, watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    if (Watcher.Event.EventType.None == watchedEvent.getType() && watchedEvent.getPath() == null) {
                        latch.countDown();
                        return;
                    }

                    if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                        try {
                            System.out.println(String.format("配置已更改，新值为%s", zooKeeper.getData(watchedEvent.getPath(), true, stat)));
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
