package com.backstreetbrogrammer.loom.futures;

import com.backstreetbrogrammer.loom.model.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FuturesBasedOMS {

    private static final AtomicInteger clientCounter = new AtomicInteger();
    private static final ExecutorService connectionHandlerPool = Executors.newFixedThreadPool(4);
    private static final ExecutorService orderHandlerPool = Executors.newFixedThreadPool(12);

    public static void main(final String[] args) throws IOException {
        final var port = 8080;
        final var serverSocket = new ServerSocket(port);
        System.out.printf("Listening on port %d%n", port);

        try {
            while (!serverSocket.isClosed()) {
                final var socket = serverSocket.accept(); // blocks and socket can never be null
                connectionHandlerPool.execute(() -> handle(socket, clientCounter.addAndGet(1)));
            }
        } finally {
            connectionHandlerPool.close();
            orderHandlerPool.close();
        }
    }

    private static void handle(final Socket socket, final int clientNo) {
        System.out.println("\n----------------------------");
        System.out.printf("Connected to Client-%d on socket=[%s]%n", clientNo, socket);
        try (
                socket
        ) {
            final var start = Instant.now();
            final var request = new Request(socket);

            final var orderValidateFuture =
                    CompletableFuture.supplyAsync(() -> ClientWallet.validate(request), orderHandlerPool);
            final var orderEnrichFuture =
                    CompletableFuture.supplyAsync(() -> MarketData.enrich(request), orderHandlerPool);
            final var orderPersistFuture =
                    CompletableFuture.supplyAsync(() -> OrderStatePersist.persist(request), orderHandlerPool);

            new Order(request)
                    .validate(orderValidateFuture.join())
                    .enrich(orderEnrichFuture.join())
                    .persist(orderPersistFuture.join())
                    .sendToDownstream();

            final var timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("%nOrder sent to downstream in [%d] ms%n%n", timeElapsed);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.printf("Disconnected from Client-%d on socket=[%s]%n", clientNo, socket);
            System.out.println("----------------------------\n");
        }
    }

}
