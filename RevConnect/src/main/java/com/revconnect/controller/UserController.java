package com.revconnect.controller;

import com.revconnect.model.User;
import com.revconnect.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import static org.revconnect.RevConnect.loggedInUser;
import static org.revconnect.RevConnect.dashboard;

public class UserController {
    static Scanner sc = new Scanner(System.in);
    static UserService service = new UserService();
    private static final Logger logger = LogManager.getLogger(UserController.class);


    // ================= REGISTER =================
    public static void register() {
        try {
            sc.nextLine();

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            System.out.print("Account Type (PERSONAL / CREATOR / BUSINESS): ");
            String accountType = sc.nextLine().toUpperCase();

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(accountType);

            boolean success = service.register(user);

            if (success) {
                logger.info("User registered successfully: {}", username);
                System.out.println("\n Registered Successfully!");
            } else {
                logger.warn("Registration failed for username: {}", username);
                System.out.println("\n Registration Failed (Username or Email may already exist)");
            }

        } catch (Exception e) {
            logger.error("Error during user registration", e);
            System.out.println("Error during registration");
        }
    }


    // ================= LOGIN =================
    public static void login() {
        try {
            logger.info("Login attempt");

            sc.nextLine();

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            loggedInUser = service.login(username, password);

            if (loggedInUser != null) {
                logger.info("Login successful for user: {}", username);
                dashboard();
            } else {
                logger.warn("Invalid login attempt: {}", username);
                System.out.println("\nInvalid username or password");
            }

        } catch (Exception e) {
            logger.error("Login error", e);
        }
    }


}
