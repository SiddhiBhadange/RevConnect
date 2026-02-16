package com.revconnect.controller;

import com.revconnect.service.FollowService;
import com.revconnect.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class FollowController {

    static Scanner sc = new Scanner(System.in);
    static FollowService followService = new FollowService();
    static NotificationService notificationService = new NotificationService();

    private static final Logger logger =
            LogManager.getLogger(FollowController.class);

    // ====================== FOLLOWS =================
    public static void followMenu() {
        try {
            while (true) {
                System.out.println("\n=== FOLLOW MENU ===");
                System.out.println("1. Follow User");
                System.out.println("2. Unfollow User");
                System.out.println("3. View My Followers");
                System.out.println("4. View My Following");
                System.out.println("5. Back");
                System.out.print("Choose option: ");

                int choice = readInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter User ID to follow: ");
                        int id = readInt();

                        followService.followUser(loggedInUser.getId(), id);
                        logger.info("UserId={} followed userId={}",
                                loggedInUser.getId(), id);

                        // Notification
                        notificationService.createNotification(
                                loggedInUser.getId(),
                                "You followed user ID " + id,
                                "FOLLOW"
                        );
                    }

                    case 2 -> {
                        System.out.print("Enter User ID to unfollow: ");
                        int id = readInt();

                        followService.unfollowUser(loggedInUser.getId(), id);
                        logger.info("UserId={} unfollowed userId={}",
                                loggedInUser.getId(), id);
                    }

                    case 3 -> {
                        logger.info("Viewing followers for userId={}",
                                loggedInUser.getId());

                        List<Integer> followers =
                                followService.getFollowers(loggedInUser.getId());

                        System.out.println("\n=== MY FOLLOWERS ===");
                        if (followers.isEmpty()) {
                            logger.warn("No followers for userId={}",
                                    loggedInUser.getId());
                            System.out.println("No followers yet");
                        } else {
                            followers.forEach(f ->
                                    System.out.println("User ID: " + f));
                        }
                    }

                    case 4 -> {
                        logger.info("Viewing following list for userId={}",
                                loggedInUser.getId());

                        List<Integer> following =
                                followService.getFollowing(loggedInUser.getId());

                        System.out.println("\n=== MY FOLLOWING ===");
                        if (following.isEmpty()) {
                            logger.warn("UserId={} is not following anyone",
                                    loggedInUser.getId());
                            System.out.println("Not following anyone");
                        } else {
                            following.forEach(f ->
                                    System.out.println("User ID: " + f));
                        }
                    }

                    case 5 -> {
                        logger.info("Exiting follow menu for userId={}",
                                loggedInUser.getId());
                        return;
                    }

                    default -> {
                        logger.warn("Invalid follow menu choice={} by userId={}",
                                choice, loggedInUser.getId());
                        System.out.println("Invalid choice!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in follow menu", e);
            System.out.println("Error while processing follow menu.");
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
