package com.company;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerHandler {
    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        System.out.println("请求建立连接");
    }

    public String handleRead(SelectionKey key) throws IOException{
        SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        String received = "";
        if (socketChannel.read(buffer) == -1) {
            socketChannel.shutdownOutput();
            socketChannel.shutdownInput();
            socketChannel.close();
            System.out.println("关闭连接");
        } else {
            buffer.flip();
            received = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
            buffer.clear();

            buffer = buffer.put(("received: " + received).getBytes("UTF-8"));
            socketChannel.write(buffer);
            socketChannel.configureBlocking(false)
                    .register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        }
        return received;
    }
}
