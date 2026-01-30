package com.revconnect.dao;


import com.revconnect.database.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowDAO {

    // Follow user
    public boolean follow(int followerId, int followingId) {
        String sql = "INSERT INTO follows(follower_id, following_id) VALUES(?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error following user: " + e.getMessage());
        }
        return false;
    }

    // Unfollow user
    public boolean unfollow(int followerId, int followingId) {
        String sql = "DELETE FROM follows WHERE follower_id = ? AND following_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error unfollowing user: " + e.getMessage());
        }
        return false;
    }

    // Get followers
    public List<Integer> getFollowers(int userId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT follower_id FROM follows WHERE following_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("follower_id"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching followers: " + e.getMessage());
        }
        return list;
    }

    // Get following
    public List<Integer> getFollowing(int userId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT following_id FROM follows WHERE follower_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("following_id"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching following: " + e.getMessage());
        }
        return list;
    }
}
