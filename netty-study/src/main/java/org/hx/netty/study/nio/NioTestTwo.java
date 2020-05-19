package org.hx.netty.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Upoint0002
 */
public class NioTestTwo {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("E:\\study_notes\\netty-study\\src\\main\\resources\\in.txt");
        FileOutputStream outputStream = new FileOutputStream("E:\\study_notes\\netty-study\\src\\main\\resources\\out.txt");
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outStreamChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            int read = inputStreamChannel.read(byteBuffer);

            System.out.println(read);

            if (-1 == read) {
                break;
            }

            byteBuffer.flip();
            outStreamChannel.write(byteBuffer);
        }

    }
}
