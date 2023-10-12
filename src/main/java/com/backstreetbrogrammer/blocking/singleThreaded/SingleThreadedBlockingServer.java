package com.backstreetbrogrammer.blocking.singleThreaded;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedBlockingServer {

    public static void main(final String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            final Socket socket = serverSocket.accept(); // this blocks and socket can never be null
            handle(socket);
        }
    }

    private static void handle(final Socket socket) throws IOException {
        System.out.printf("Connected to %s%n", socket);
        try (
                socket;
                final InputStream in = socket.getInputStream();
                final OutputStream out = socket.getOutputStream()
        ) {
            //in.transferTo(out); // default buffer size is 8192
            int data;
            while ((data = in.read()) != -1) { // read 1 byte at a time and -1 means EOF
                out.write(transmogrify(data));
            }
        } finally {
            System.out.printf("Disconnected from %s%n", socket);
        }
    }

    private static int transmogrify(final int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}
