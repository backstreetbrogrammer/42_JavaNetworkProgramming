package com.backstreetbrogrammer.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPDemo {

    public static void main(final String[] args) {
        try {
            final int port = 5000;

            // start the server
            final GuidemyUDPServer server = new GuidemyUDPServer(port);
            new Thread(server).start();

            // create the client
            final var socket = new DatagramSocket();
            final var localhost = InetAddress.getByName("localhost");
            final var client = new GuidemyUDPClient(socket, localhost, port);

            try (final Scanner scanner = new Scanner(System.in)) {
                String echoString;
                do {
                    System.out.println("Enter string to be echoed: ");
                    echoString = scanner.nextLine();

                    final String echoReceivedFromServer = client.sendEcho(echoString);
                    System.out.printf("Echo received from server: [%s]%n", echoReceivedFromServer);
                    System.out.println("------------------------------------");
                } while (!"quit".equalsIgnoreCase(echoString));
            } finally {
                client.close();
                server.stop();
            }
        } catch (final SocketException e) {
            System.err.printf("SocketException: %s%n", e.getMessage());
        } catch (final IOException e) {
            System.err.printf("IOException: %s%n", e.getMessage());
        }
    }
}
