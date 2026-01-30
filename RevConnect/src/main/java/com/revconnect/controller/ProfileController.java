package com.revconnect.controller;

import com.revconnect.model.Profile;
import com.revconnect.service.ProfileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.revconnect.RevConnect.loggedInUser;

import java.util.List;
import java.util.Scanner;

public class ProfileController {

    static Scanner sc = new Scanner(System.in);
    static ProfileService profileService = new ProfileService();

    private static final Logger logger =
            LogManager.getLogger(ProfileController.class);

    // ================= PROFILE =================
    public static void viewProfile() {
        try {
            logger.info("Viewing profile for userId={}", loggedInUser.getId());

            System.out.println("\n=== MY PROFILE ===");
            System.out.println("ID: " + loggedInUser.getId());
            System.out.println("Username: " + loggedInUser.getUsername());
            System.out.println("Account Type: " + loggedInUser.getUserType());

            Profile profile =
                    profileService.getProfileByUserId(loggedInUser.getId());

            if (profile == null) {
                logger.warn("Profile not found for userId={}", loggedInUser.getId());
                System.out.println("\nProfile not created yet.");
                return;
            }

            System.out.println("Name: " + profile.getName());
            System.out.println("Bio: " +
                    (profile.getBio() == null || profile.getBio().isEmpty()
                            ? "Not set" : profile.getBio()));
            System.out.println("Location: " +
                    (profile.getLocation() == null || profile.getLocation().isEmpty()
                            ? "Not set" : profile.getLocation()));
            System.out.println("Website: " +
                    (profile.getWebsite() == null || profile.getWebsite().isEmpty()
                            ? "Not set" : profile.getWebsite()));

        } catch (Exception e) {
            logger.error("Error while viewing profile", e);
            System.out.println("Error while loading profile.");
        }
    }

    public static void editProfile() {
        try {
            Profile profile =
                    profileService.getProfileByUserId(loggedInUser.getId());

            if (profile == null) {
                logger.info("Creating new profile for userId={}", loggedInUser.getId());
                profile = new Profile();
                profile.setUserId(loggedInUser.getId());
            }

            System.out.print("Name [" + (profile.getName() != null ? profile.getName() : "") + "]: ");
            String name = sc.nextLine();
            if (!name.isBlank()) profile.setName(name);

            System.out.print("Bio [" + (profile.getBio() != null ? profile.getBio() : "") + "]: ");
            String bio = sc.nextLine();
            if (!bio.isBlank()) profile.setBio(bio);

            System.out.print("Location [" + (profile.getLocation() != null ? profile.getLocation() : "") + "]: ");
            String location = sc.nextLine();
            if (!location.isBlank()) profile.setLocation(location);

            System.out.print("Website [" + (profile.getWebsite() != null ? profile.getWebsite() : "") + "]: ");
            String website = sc.nextLine();
            if (!website.isBlank()) profile.setWebsite(website);

            boolean success = profileService.updateProfile(profile);

            if (success) {
                logger.info("Profile updated for userId={}", loggedInUser.getId());
                System.out.println(" Profile updated!");
            } else {
                logger.warn("Profile update failed for userId={}", loggedInUser.getId());
                System.out.println(" Failed to update profile");
            }

        } catch (Exception e) {
            logger.error("Error while editing profile", e);
            System.out.println("Error while updating profile.");
        }
    }

    public static void searchUsers() {
        try {
            sc.nextLine();
            System.out.print("Enter name or username to search: ");
            String keyword = sc.nextLine();

            logger.info("Searching profiles with keyword='{}'", keyword);

            List<Profile> results =
                    profileService.searchProfiles(keyword);

            if (results.isEmpty()) {
                logger.warn("No profiles found for keyword='{}'", keyword);
                System.out.println("No users found.");
                return;
            }

            System.out.println("\n=== SEARCH RESULTS ===");

            for (Profile p : results) {
                System.out.println("----------------------");
                System.out.println("User ID: " + p.getUserId());
                System.out.println("Name: " + p.getName());
                System.out.println("Bio: " + p.getBio());
                System.out.println("Location: " + p.getLocation());
                System.out.println("Website: " + p.getWebsite());
            }

            System.out.print("\nEnter User ID to view full profile (0 to cancel): ");
            int id = readInt();

            if (id != 0) {
                viewOtherProfile(id);
            }

        } catch (Exception e) {
            logger.error("Error during user search", e);
            System.out.println("Error while searching users.");
        }
    }

    static void viewOtherProfile(int userId) {
        try {
            logger.info("Viewing other user profile userId={}", userId);

            Profile profile =
                    profileService.getProfileByUserId(userId);

            if (profile == null) {
                logger.warn("Profile not found for userId={}", userId);
                System.out.println("Profile not found.");
                return;
            }

            System.out.println("\n=== USER PROFILE ===");
            System.out.println("User ID: " + profile.getUserId());
            System.out.println("Name: " + profile.getName());
            System.out.println("Bio: " + profile.getBio());
            System.out.println("Location: " + profile.getLocation());
            System.out.println("Website: " + profile.getWebsite());

        } catch (Exception e) {
            logger.error("Error while viewing other profile userId=" + userId, e);
            System.out.println("Error while loading user profile.");
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
