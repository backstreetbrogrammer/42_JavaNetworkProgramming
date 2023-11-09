package com.backstreetbrogrammer.loom.model;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Request {
    public Request(final Socket socket) {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (final InterruptedException e) {
            System.err.println(socket);
            throw new RuntimeException(e);
        }
    }
}
