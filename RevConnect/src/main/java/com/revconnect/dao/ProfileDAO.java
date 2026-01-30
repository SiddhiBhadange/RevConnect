package com.revconnect.dao;


import com.revconnect.database.config.DatabaseConnection;
import com.revconnect.model.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO {

    // Get profile by user ID
    public Profile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM profiles WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("DB connection failed!");
                return null;
            }

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToProfile(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching profile: " + e.getMessage());
        }
        return null;
    }

    // Update profile
    public boolean updateProfile(Profile profile) {
        String sql = "UPDATE profiles SET name = ?, bio = ?, location = ?, website = ? WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("DB connection failed!");
                return false;
            }

            ps.setString(1, profile.getName());
            ps.setString(2, profile.getBio());
            ps.setString(3, profile.getLocation());
            ps.setString(4, profile.getWebsite());// added profile_pic
            ps.setInt(5, profile.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating profile: " + e.getMessage());
        }
        return false;
    }

    // Save or insert profile (REPLACE INTO style)
    public boolean saveOrInsert(Profile profile) {
        String sql = "REPLACE INTO profiles(user_id, name, bio, location, website) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("DB connection failed!");
                return false;
            }

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getName());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getLocation());
            ps.setString(5, profile.getWebsite());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving profile: " + e.getMessage());
        }
        return false;
    }

    // Search profiles by keyword (name or username)
    public List<Profile> searchProfiles(String keyword) {
        List<Profile> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM profiles p JOIN users u ON p.user_id = u.id " +
                "WHERE u.username LIKE ? OR p.name LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("DB connection failed!");
                return list;
            }

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToProfile(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching profiles: " + e.getMessage());
        }
        return list;
    }

    // Mapping ResultSet to Profile object
    private Profile mapToProfile(ResultSet rs) throws SQLException {
        Profile p = new Profile();
        p.setUserId(rs.getInt("user_id"));
        p.setName(rs.getString("name"));
        p.setBio(rs.getString("bio"));
        p.setLocation(rs.getString("location"));
        p.setWebsite(rs.getString("website"));
        return p;
    }
}
