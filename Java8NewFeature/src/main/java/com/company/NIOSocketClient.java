package com.company;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOSocketClient {
    public void run() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int sendCount = 0;
            while (sendCount < 10) {
                buffer.clear();
                buffer.put(("current time: " + System.currentTimeMillis()).getBytes("UTF-8"));
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

                int readLength = socketChannel.read(buffer);
                byte[] bytes = new byte[readLength];
                buffer.get(bytes);
                System.out.println(new String(bytes, "UTF-8"));
                buffer.clear();
                sendCount ++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOSocketClient client = new NIOSocketClient();
        client.run();
    }
}
