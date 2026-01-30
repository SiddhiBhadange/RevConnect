package com.revconnect.controller;

import com.revconnect.service.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class ConnectionController {

    static Scanner sc = new Scanner(System.in);
    static ConnectionService connectionService = new ConnectionService();

    private static final Logger logger =
            LogManager.getLogger(ConnectionController.class);

    // =================== CONNECTIONS ======================
    public static void connectionMenu() {
        try {
            while (true) {
                System.out.println("\n=== CONNECTIONS MENU ===");
                System.out.println("1. Send Connection Request");
                System.out.println("2. View & Accept/Reject Requests");
                System.out.println("3. View My Connections");
                System.out.println("4. Remove a Connection");
                System.out.println("5. Back");
                System.out.print("Choose option: ");

                int choice = readInt();
                sc.nextLine();

                switch (choice) {

                    case 1 -> {
                        System.out.print("Enter User ID to connect: ");
                        int id = readInt();

                        connectionService.sendRequest(loggedInUser.getId(), id);
                        logger.info("Connection request sent from userId={} to userId={}",
                                loggedInUser.getId(), id);
                    }

                    case 2 -> {
                        logger.info("Viewing pending connection requests for userId={}",
                                loggedInUser.getId());

                        List<String> pending =
                                connectionService.getpendingRequests(loggedInUser.getId());

                        if (pending.isEmpty()) {
                            logger.warn("No pending connection requests for userId={}",
                                    loggedInUser.getId());
                            System.out.println("No pending requests.");
                            break;
                        }

                        System.out.println("\n=== PENDING REQUESTS ===");
                        pending.forEach(System.out::println);

                        System.out.print("Enter Request ID to respond: ");
                        int reqId = readInt();
                        sc.nextLine();

                        String status;
                        while (true) {
                            System.out.print("Enter Status (ACCEPTED / REJECTED): ");
                            status = sc.nextLine().trim().toUpperCase();
                            if (status.equals("ACCEPTED") || status.equals("REJECTED")) break;
                            System.out.println("Invalid status! Enter ACCEPTED or REJECTED.");
                        }

                        if (connectionService.updateStatus(reqId, status)) {
                            logger.info("Connection requestId={} {} by userId={}",
                                    reqId, status, loggedInUser.getId());
                            System.out.println("Request " + status.toLowerCase() + " successfully.");
                        } else {
                            logger.warn("Failed to update connection requestId={} by userId={}",
                                    reqId, loggedInUser.getId());
                            System.out.println("Failed to update request.");
                        }
                    }

                    case 3 -> {
                        logger.info("Viewing connections for userId={}",
                                loggedInUser.getId());

                        List<String> connections =
                                connectionService.getConnections(loggedInUser.getId());

                        System.out.println("\n=== MY CONNECTIONS ===");
                        if (connections.isEmpty()) {
                            logger.warn("No connections found for userId={}",
                                    loggedInUser.getId());
                            System.out.println("No connections yet.");
                        } else {
                            connections.forEach(System.out::println);
                        }
                    }

                    case 4 -> {
                        List<String> connections =
                                connectionService.getConnections(loggedInUser.getId());

                        if (connections.isEmpty()) {
                            logger.warn("No connections to remove for userId={}",
                                    loggedInUser.getId());
                            System.out.println("No connections to remove.");
                            break;
                        }

                        System.out.println("\n=== MY CONNECTIONS ===");
                        connections.forEach(System.out::println);

                        System.out.print("Enter User ID of the connection to remove: ");
                        int userIdToRemove = readInt();

                        if (connectionService.removeConnection(
                                loggedInUser.getId(), userIdToRemove)) {

                            logger.info("Connection removed between userId={} and userId={}",
                                    loggedInUser.getId(), userIdToRemove);
                            System.out.println("Connection removed.");
                        } else {
                            logger.warn("Failed to remove connection between userId={} and userId={}",
                                    loggedInUser.getId(), userIdToRemove);
                            System.out.println("Failed to remove connection.");
                        }
                    }

                    case 5 -> {
                        logger.info("Exiting connections menu for userId={}",
                                loggedInUser.getId());
                        return;
                    }

                    default -> {
                        logger.warn("Invalid connections menu choice={} by userId={}",
                                choice, loggedInUser.getId());
                        System.out.println("Invalid choice!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in connection menu", e);
            System.out.println("Error while processing connections.");
        }
    }

    // ================= UTIL =================
    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
