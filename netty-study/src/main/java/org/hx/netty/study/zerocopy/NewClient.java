package org.hx.netty.study.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Upoint0002
 */
public class NewClient {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("E:\\ws-service-0.0.1-SNAPSHOT.jar");
        FileChannel fileChannel = inputStream.getChannel();

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8899));
        socketChannel.configureBlocking(true);

        long startTime = System.currentTimeMillis();

        long readCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送总字节数：" + readCount + ",耗时" + (System.currentTimeMillis() - startTime));

        fileChannel.close();
        inputStream.close();
    }
}
