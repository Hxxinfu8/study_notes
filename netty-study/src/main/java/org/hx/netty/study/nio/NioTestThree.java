package org.hx.netty.study.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Upoint0002
 */
public class NioTestThree {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("E:\\study_notes\\netty-study\\src\\main\\resources\\in.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 54);
        mappedByteBuffer.put((byte) 1);
        mappedByteBuffer.put((byte) 2);
        file.close();
    }
}
