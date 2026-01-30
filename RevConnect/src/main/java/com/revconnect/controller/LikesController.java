package com.revconnect.controller;

import com.revconnect.service.LikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class LikesController {

    static Scanner sc = new Scanner(System.in);
    static LikeService likeService = new LikeService();

    private static final Logger logger =
            LogManager.getLogger(LikesController.class);

    // ================= LIKES ===========================
    public static void likeMenu(int userId) {
        try {
            System.out.print("Enter Post ID to like: ");
            int postId = Integer.parseInt(sc.nextLine());

            likeService.like(userId, postId);

            logger.info("Post liked postId={} by userId={}", postId, userId);

        } catch (NumberFormatException e) {
            logger.warn("Invalid postId input while liking by userId={}", userId);
            System.out.println("Please enter a valid post ID.");
        } catch (Exception e) {
            logger.error("Error while liking post by userId=" + userId, e);
            System.out.println("Error while liking post.");
        }
    }

    public static void unlikeMenu(int userId) {
        try {
            System.out.print("Enter Post ID to unlike: ");
            int postId = Integer.parseInt(sc.nextLine());

            likeService.unlike(userId, postId);

            logger.info("Post unliked postId={} by userId={}", postId, userId);

        } catch (NumberFormatException e) {
            logger.warn("Invalid postId input while unliking by userId={}", userId);
            System.out.println("Please enter a valid post ID.");
        } catch (Exception e) {
            logger.error("Error while unliking post by userId=" + userId, e);
            System.out.println("Error while unliking post.");
        }
    }
}
