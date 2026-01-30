package com.revconnect.controller;

import com.revconnect.model.Post;
import com.revconnect.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class PostController {

    static Scanner sc = new Scanner(System.in);
    static PostService postService = new PostService();

    private static final Logger logger =
            LogManager.getLogger(PostController.class);

    // ================= POST FUNCTIONS =================
    public static void createPost() {
        try {
            sc.nextLine();

            System.out.print("Enter post content: ");
            String content = sc.nextLine();

            System.out.print("Enter hashtags: ");
            String hashtags = sc.nextLine();

            Post post = new Post();
            post.setUserId(loggedInUser.getId());
            post.setContent(content);
            post.setHashtags(hashtags);

            boolean success = postService.createPost(post);

            if (success) {
                logger.info("Post created by userId={}", loggedInUser.getId());
                System.out.println(" Post created successfully!");
            } else {
                logger.warn("Post creation failed for userId={}", loggedInUser.getId());
                System.out.println(" Failed to create post");
            }

        } catch (Exception e) {
            logger.error("Error while creating post", e);
            System.out.println("Error while creating post.");
        }
    }

    public static void viewMyPosts() {
        try {
            logger.info("Viewing posts for userId={}", loggedInUser.getId());

            List<Post> posts =
                    postService.getMyPosts(loggedInUser.getId());

            System.out.println("\n=== MY POSTS ===");

            if (posts.isEmpty()) {
                logger.warn("No posts found for userId={}", loggedInUser.getId());
                System.out.println("No posts found");
                return;
            }

            for (Post p : posts) {
                System.out.println("---------------------------");
                System.out.println("Post ID: " + p.getId());
                System.out.println("Content: " + p.getContent());
                System.out.println("Hashtags: " + p.getHashtags());
                System.out.println("Created: " + p.getCreatedAt());
            }

        } catch (Exception e) {
            logger.error("Error while viewing posts", e);
            System.out.println("Error while loading posts.");
        }
    }

    public static void deletePost() {
        try {
            System.out.print("Enter Post ID to delete: ");
            int postId = sc.nextInt();

            boolean success =
                    postService.deletePost(postId, loggedInUser.getId());

            if (success) {
                logger.info("Post deleted postId={} by userId={}",
                        postId, loggedInUser.getId());
                System.out.println(" Post deleted!");
            } else {
                logger.warn("Unauthorized or missing postId={} for userId={}",
                        postId, loggedInUser.getId());
                System.out.println(" Post not found or unauthorized");
            }

        } catch (Exception e) {
            logger.error("Error while deleting post", e);
            System.out.println("Error while deleting post.");
            sc.nextLine(); // clear buffer
        }
    }

    public static void editPost() {
        try {
            System.out.print("Enter Post ID to edit: ");
            int postId = readInt();
            sc.nextLine();

            System.out.print("New content: ");
            String content = sc.nextLine();

            System.out.print("New hashtags: ");
            String hashtags = sc.nextLine();

            boolean success = postService.updatePost(
                    postId,
                    loggedInUser.getId(),
                    content,
                    hashtags
            );

            if (success) {
                logger.info("Post updated postId={} by userId={}",
                        postId, loggedInUser.getId());
                System.out.println(" Post updated!");
            } else {
                logger.warn("Post update failed postId={} by userId={}",
                        postId, loggedInUser.getId());
                System.out.println(" Failed to update post");
            }

        } catch (Exception e) {
            logger.error("Error while editing post", e);
            System.out.println("Error while editing post.");
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
