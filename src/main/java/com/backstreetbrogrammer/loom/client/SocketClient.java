package com.backstreetbrogrammer.loom.client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketClient {

    public static void main(final String[] args) throws IOException, InterruptedException {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Specify the number of sockets to create and connect");
        }

        final var numberOfSocketsToCreate = Integer.parseInt(args[0]);
        if (numberOfSocketsToCreate <= 0) {
            throw new IllegalArgumentException("Number of sockets connection should be greater than 0");
        }

        final var sockets = new Socket[numberOfSocketsToCreate];

        // connect
        for (var i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 8080);
            System.out.printf("Connected: [%s]%n", sockets[i]);
        }

        TimeUnit.SECONDS.sleep(1L);

        // disconnect
        for (final var socket : sockets) {
            if (socket != null) {
                socket.close();
                System.out.printf("Disconnected: [%s]%n", socket);
            }
        }
    }

}
