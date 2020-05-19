package org.hx.netty.study.nio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Upoint0002
 */
public class NioServer {
    private static Map<String, SocketChannel> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                final SocketChannel client;
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                        client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println(client.getRemoteAddress().toString() + "尝试连接");
                        map.put(client.getRemoteAddress().toString(), client);
                    } else if (selectionKey.isReadable()) {
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(byteBuffer);
                        if (count > 0) {
                            byteBuffer.flip();
                            String message = String.valueOf(CharsetUtil.decoder(CharsetUtil.UTF_8).decode(byteBuffer).array());
                            String key = "";
                            for(Map.Entry<String, SocketChannel> entry : map.entrySet()) {
                                if (entry.getValue() == client) {
                                    key = entry.getKey();
                                    break;
                                }
                            }

                            System.out.println("【" + key + "】：" + message);

                            for(Map.Entry<String, SocketChannel> entry : map.entrySet()) {
                                if (!entry.getKey().equals(key)) {
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put(("【" + key + "】：" + message).getBytes());
                                    writeBuffer.flip();
                                    entry.getValue().write(writeBuffer);
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            selectionKeys.clear();
        }
    }
}
