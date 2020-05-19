package org.hx.netty.study.nio;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * @author Upoint0002
 */
public class NioTestOne {
    public static void main(String[] args) {
        IntBuffer in = IntBuffer.allocate(10);

        for (int i = 0; i < in.capacity(); i ++) {
            System.out.println(in.position());
            System.out.println(in.limit());
            in.put(new Random().nextInt(10));
        }

        in.flip();

        System.out.println("-------------------------");

        while (in.hasRemaining()) {
            System.out.println(in.position());
            System.out.println(in.limit());
            in.get();
        }
    }
}
