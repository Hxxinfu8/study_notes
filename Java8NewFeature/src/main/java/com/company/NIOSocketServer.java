package com.company;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NIOSocketServer {
    private volatile boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void run() {
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ServerHandler serverHandler = new ServerHandler();

            if (flag) {
                selector.select();
                Iterator<SelectionKey> keyIterators = selector.selectedKeys().iterator();
                while (keyIterators.hasNext()) {
                    SelectionKey selectionKey = keyIterators.next();
                    if (selectionKey.isAcceptable()) {
                        serverHandler.handleAccept(selectionKey);
                    }

                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        serverHandler.handleRead(selectionKey);
                    }

                    keyIterators.remove();
                }

                System.out.println("请求处理完成");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOSocketServer socketServer = new NIOSocketServer();
//        new Thread(() ->{
//            try{
//                Thread.sleep(50000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                socketServer.setFlag(false);
//            }
//        }).start();
        socketServer.run();
    }
}
