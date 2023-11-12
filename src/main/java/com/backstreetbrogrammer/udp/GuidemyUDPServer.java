package com.backstreetbrogrammer.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GuidemyUDPServer implements Runnable {

    private final DatagramSocket socket;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private byte[] buf = new byte[256];

    public GuidemyUDPServer(final int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (final SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (running.get()) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                final String received = new String(buf, 0, packet.getLength());
                System.out.printf("[UDP-Server] Received text from client: [%s]%n", received);

                final InetAddress address = packet.getAddress();
                final int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);

                // echo same back to the client
                socket.send(packet);

                if ("quit".equalsIgnoreCase(received)) {
                    running.set(false);
                }
            } catch (final SocketException e) {
                System.err.printf("[UDP-Server] SocketException: %s%n", e.getMessage());
            } catch (final IOException e) {
                System.err.printf("[UDP-Server] IOException: %s%n", e.getMessage());
            }
        }
        socket.close();
    }

    public void stop() {
        running.set(false);
    }
}
