package org.revconnect;
import com.revconnect.controller.*;
import com.revconnect.model.*;
import com.revconnect.service.*;
import java.util.Scanner;

public class RevConnect {
    static Scanner sc = new Scanner(System.in);
    public static User loggedInUser = null;
    static UserController userController = new UserController();
    static PostController postController =new PostController();
    static ProfileController profileController = new ProfileController();
    static CommentController commentController = new CommentController();
    static LikesController likesController = new LikesController();
    static NotificationController notificationController = new NotificationController();
    static FollowController followController = new FollowController();
    static ConnectionController  connectionController = new ConnectionController();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== REVCONNECT ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> userController.register();
                case 2 -> userController.login();
                case 3 -> {
                    System.out.println("Thank You!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }


    // ================= DASHBOARD =================
  public static void dashboard() {
        while (true) {
            System.out.println("\n=== DASHBOARD ===");
            System.out.println("1. Create Post");
            System.out.println("2. View My Posts");
            System.out.println("3. Edit My Post");
            System.out.println("4. Delete Post");
            System.out.println("5. Comments");
            System.out.println("6. Notifications");
            System.out.println("7. View Profile");
            System.out.println("8. Like/Unlike Posts");
            System.out.println("9. Follow");
            System.out.println("10. Search Users");
            System.out.println("11. Connections");
            System.out.println("12. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> postController.createPost();
                case 2 -> postController.viewMyPosts();
                case 3 -> postController.editPost();
                case 4 -> postController.deletePost();
                case 5 -> commentController.openComments();
                case 6 -> notificationController.openNotifications();
                case 7 -> profileMenu();
                case 8 -> likesMenu();
                case 9 -> followController.followMenu();
                case 10 -> profileController.searchUsers();
                case 11 -> connectionController.connectionMenu();
                case 12 -> {
                    loggedInUser = null;
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    // ================= PROFILE MENU =================
    static void profileMenu() {
        while (true) {
            System.out.println("\n=== PROFILE MENU ===");
            System.out.println("1. View My Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = readInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> profileController.viewProfile(); // existing method
                case 2 -> profileController.editProfile();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }


    // ================= LIKES MENU =================
    static void likesMenu() {
        System.out.println("\n=== LIKE MENU ===");
        System.out.println("1. Like Post");
        System.out.println("2. Unlike Post");
        System.out.println("3. Back");
        System.out.print("Choose option: ");

        int choice = readInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> likesController.likeMenu(loggedInUser.getId());
            case 2 -> likesController.unlikeMenu(loggedInUser.getId());
            case 3 -> { return; }
            default -> System.out.println("Invalid choice! Try again.");
        }
    }
    // ================= UTIL =================
    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next(); // consume invalid input
        }
        return sc.nextInt();
    }
}
