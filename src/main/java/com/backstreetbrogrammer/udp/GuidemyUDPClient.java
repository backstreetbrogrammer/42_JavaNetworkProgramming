package com.backstreetbrogrammer.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class GuidemyUDPClient {

    private final DatagramSocket socket;
    private final InetAddress address;

    private final int port;
    private byte[] buf;

    public GuidemyUDPClient(final DatagramSocket socket, final InetAddress address, final int port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    public String sendEcho(final String msg) {
        try {
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (final SocketTimeoutException e) {
            System.out.println("[UDP-Client] The socket timed out");
        } catch (final IOException e) {
            System.out.printf("[UDP-Client] Error: %s%n", e.getMessage());
        }
        return null;
    }

    public void close() {
        socket.close();
    }
}
