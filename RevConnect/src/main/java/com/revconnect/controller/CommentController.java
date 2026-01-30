package com.revconnect.controller;

import com.revconnect.model.Comment;
import com.revconnect.service.CommentService;
import com.revconnect.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class CommentController  {

    static Scanner sc = new Scanner(System.in);
    static CommentService commentService = new CommentService();
    static NotificationService notificationService = new NotificationService();

    private static final Logger logger =
            LogManager.getLogger(CommentController.class);

    // ================= COMMENTS =================
    public static void openComments() {
        try {
            System.out.print("Enter Post ID: ");
            int postId = sc.nextInt();
            sc.nextLine();

            logger.info("Opening comments for postId={} by userId={}",
                    postId, loggedInUser.getId());

            while (true) {
                System.out.println("\n=== COMMENTS ===");
                System.out.println("1. View Comments");
                System.out.println("2. Add Comment");
                System.out.println("3. Back");

                System.out.print("Choose option: ");
                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {

                    case 1 -> {
                        logger.info("Viewing comments for postId={}", postId);

                        List<Comment> comments =
                                commentService.getCommentsByPost(postId);

                        if (comments.isEmpty()) {
                            logger.warn("No comments found for postId={}", postId);
                            System.out.println("No comments yet");
                        } else {
                            for (Comment c : comments) {
                                System.out.println("---------------------");
                                System.out.println("User ID: " + c.getUserId());
                                System.out.println("Comment: " + c.getContent());
                                System.out.println("Created: " + c.getCreatedAt());
                            }
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter comment: ");
                        String content = sc.nextLine();

                        Comment comment = new Comment();
                        comment.setPostId(postId);
                        comment.setUserId(loggedInUser.getId());
                        comment.setContent(content);

                        boolean success =
                                commentService.addComment(comment);

                        if (success) {
                            logger.info("Comment added on postId={} by userId={}",
                                    postId, loggedInUser.getId());

                            System.out.println(" Comment added!");

                            // 🔔 Notify post owner
                            notificationService.createNotification(
                                    loggedInUser.getId(),
                                    "You commented on Post ID " + postId,
                                    "COMMENT"
                            );
                        } else {
                            logger.warn("Failed to add comment on postId={} by userId={}",
                                    postId, loggedInUser.getId());
                            System.out.println(" Failed to add comment");
                        }
                    }

                    case 3 -> {
                        logger.info("Exiting comments menu for postId={} by userId={}",
                                postId, loggedInUser.getId());
                        return;
                    }

                    default -> {
                        logger.warn("Invalid comment menu choice={} for postId={}",
                                ch, postId);
                        System.out.println("Invalid choice!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in comment menu", e);
            System.out.println("Error while processing comments.");
            sc.nextLine();
        }
    }
}
