package org.hx.netty.study.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class FileClient {
    public static void main(String[] args) {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
            channel.connect(new InetSocketAddress(8080));

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    if (selectionKey.isConnectable()) {
                        try {
                            if (socketChannel.isConnectionPending()) {
                                socketChannel.finishConnect();
                            }
                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                            System.out.println("客户端连接成功");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (selectionKey.isWritable()) {
                        try (FileChannel fileChannel = new FileInputStream("F:\\financepj_new.sql").getChannel()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(524288000);
                            while (fileChannel.position() < fileChannel.size()) {
                                fileChannel.read(byteBuffer);//从文件通道读取到byteBuffer
                                byteBuffer.flip();
                                while (byteBuffer.hasRemaining()) {
                                    socketChannel.write(byteBuffer);//写入通道
                                }
                                byteBuffer.clear();//清理byteBuffer
                                System.out.println(fileChannel.position() + " " + fileChannel.size());
                            }
                            System.out.println("结束写操作");
                            socketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
