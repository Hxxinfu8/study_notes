package org.hx.netty.study.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Upoint0002
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8899));
        FileInputStream fileInputStream = new FileInputStream("E:\\ws-service-0.0.1-SNAPSHOT.jar");

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(bytes)) > 0) {
            total += readCount;
            dataOutputStream.write(bytes);
        }
        System.out.println("发送总字节数：" + total + ",耗时" + (System.currentTimeMillis() - startTime));
        fileInputStream.close();
        socket.close();
        dataOutputStream.close();
    }
}
