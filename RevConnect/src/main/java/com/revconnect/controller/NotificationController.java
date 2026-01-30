package com.revconnect.controller;

import com.revconnect.model.Notification;
import com.revconnect.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class NotificationController {

    static Scanner sc = new Scanner(System.in);
    static NotificationService notificationService = new NotificationService();

    private static final Logger logger =
            LogManager.getLogger(NotificationController.class);

    // ================= NOTIFICATIONS =================
    public static void openNotifications() {
        try {
            logger.info("Opening notifications for userId={}", loggedInUser.getId());

            System.out.println("\n=== NOTIFICATIONS ===");

            List<Notification> notifications =
                    notificationService.getUserNotifications(loggedInUser.getId());

            if (notifications.isEmpty()) {
                logger.warn("No notifications found for userId={}", loggedInUser.getId());
                System.out.println("No notifications");
            } else {
                for (Notification n : notifications) {
                    System.out.println("-----------------------");
                    System.out.println("Message: " + n.getMessage());
                    System.out.println("Type: " + n.getType());
                    System.out.println("Read: " + (n.isRead() ? "Yes" : "No"));
                    System.out.println("Created: " + n.getCreatedAt());
                }
            }

            System.out.print("\nMark all as read? (Y/N): ");
            String choice = sc.next();

            if (choice.equalsIgnoreCase("Y")) {
                notificationService.markAllAsRead(loggedInUser.getId());
                logger.info("All notifications marked as read for userId={}",
                        loggedInUser.getId());
                System.out.println(" Notifications marked as read");
            }

        } catch (Exception e) {
            logger.error("Error while opening notifications", e);
            System.out.println("Error while loading notifications.");
        }
    }
}
