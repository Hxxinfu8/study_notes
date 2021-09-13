package org.hx.netty.study.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class FileServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        //获得客户端连接通道
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);//可以在任意位置调用这个方法，新的阻塞模式只会影响下面的i/o操作
                        //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_WRITE事件。
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("客户端请求连接事件");
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                        int readLength = channel.read(byteBuffer);
                        byteBuffer.flip();
                        int count = 0;
                        File file = new File("C:\\Users\\Upoint0002\\Desktop\\financepj_new.sql");
                        if (!file.exists()) file.createNewFile();
                        try (FileChannel outFileChannel = new FileOutputStream(file, true).getChannel()) {
                            while (readLength != -1) { //分多次读取
                                count = count + readLength;
                                System.out.println("count=" + count + " readLength=" + readLength);
                                readLength = channel.read(byteBuffer);//将socketChannel数据读到byteBuffer
                                byteBuffer.flip();
                                outFileChannel.write(byteBuffer);//byteBuffer数据写到FileChannel
                                byteBuffer.clear();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("读取结束");
                        channel.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
