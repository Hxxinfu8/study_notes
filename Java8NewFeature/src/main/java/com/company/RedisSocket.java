package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisSocket {
    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket server = serverSocket.accept();
            byte[] bytes = new byte[1024];
            server.getInputStream().read(bytes);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void set(Socket socket, String key, String value) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*3\n")
                .append("$3\n")
                .append("set\n")
                .append("$\n")
                .append(key.getBytes().length)
                .append("\n")
                .append(key)
                .append("\n")
                .append("$")
                .append(value.getBytes().length)
                .append("\n");
        socket.getOutputStream().write(stringBuilder.toString().getBytes());
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
