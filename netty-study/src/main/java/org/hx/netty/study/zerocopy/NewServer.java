package org.hx.netty.study.zerocopy;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Upoint0002
 */
public class NewServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        serverSocketChannel.bind(new InetSocketAddress(8899));

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(true);

            int read = 0;
            while (read != -1) {
               try {
                   read = channel.read(byteBuffer);
               } catch (Exception e) {
                   e.printStackTrace();
               }
                byteBuffer.rewind();
            }
        }

    }
}
