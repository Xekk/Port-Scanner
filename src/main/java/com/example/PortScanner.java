package com.example;

import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScanner {

    private static final int TIMEOUT = 200; // ms
    private static final int THREAD_COUNT = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input
        System.out.print("Enter target IP or domain: ");
        String target = scanner.nextLine();
        target = target.replaceFirst("^https?://", "").replaceAll("/.*", "");

        System.out.print("Enter starting port: ");
        int startPort = scanner.nextInt();

        System.out.print("Enter ending port: ");
        int endPort = scanner.nextInt();

        scanner.nextLine(); // consume newline


        try {
            String resolvedIP = java.net.InetAddress.getByName(target).getHostAddress();
            System.out.println("Scanning host: " + target + " (" + resolvedIP + ")");

            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            for (int port = startPort; port <= endPort; port++) {
                final int currentPort = port;
                String finalTarget1 = target;
                executor.execute(() -> {
                    scanPort(finalTarget1, currentPort);
                });
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }

            System.out.println("Scan complete.");

        } catch (UnknownHostException e) {
            System.err.println("Could not resolve host.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void scanPort(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT);
            System.out.printf("Port %d is OPEN%n", port);

            // Try to grab a banner (optional)
            socket.setSoTimeout(500); // Short timeout for reading
            try {
                byte[] buffer = new byte[1024];
                int bytesRead = socket.getInputStream().read(buffer);
                if (bytesRead > 0) {
                    String banner = new String(buffer, 0, bytesRead);
                    System.out.println(" Banner: " + banner.trim());
                }
            } catch (Exception ignored) {
                // No banner or timeout
            }

        } catch (Exception ignored) {
            // Port is closed or filtered
        }
    }



}
