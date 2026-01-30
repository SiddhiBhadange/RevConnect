package com.revconnect.dao;

import com.revconnect.database.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDao {

    // SEND CONNECTION REQUEST
    public boolean sendRequest(int senderId, int receiverId) {

        if (senderId == receiverId) {
            System.out.println("You cannot send a connection request to yourself!");
            return false;
        }

        try (Connection con = DatabaseConnection.getConnection()) {

            // Check if connection already exists (both directions)
            String checkSql = """
            SELECT 1 FROM connections
            WHERE (sender_id = ? AND receiver_id = ?)
               OR (sender_id = ? AND receiver_id = ?)
        """;

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, senderId);
            checkPs.setInt(2, receiverId);
            checkPs.setInt(3, receiverId);
            checkPs.setInt(4, senderId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                System.out.println("Connection already exists or request already sent.");
                return false;
            }

            // Insert new request
            String sql = """
            INSERT INTO connections (sender_id, receiver_id, status)
            VALUES (?, ?, 'PENDING')
        """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Connection request sent!");
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ACCEPT / REJECT REQUEST
    public boolean updateStatus(int id, String status) {

        String sql = "UPDATE connections SET status = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toUpperCase());
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Request updated: " + status);
                return true;
            } else {
                System.out.println("No request found with ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // GET MY CONNECTIONS
    public List<String> getConnections(int userId) {
        List<String> list = new ArrayList<>();

        String sql = """
            SELECT sender_id, receiver_id
            FROM connections
            WHERE (sender_id = ? OR receiver_id = ?)
              AND status = 'ACCEPTED'
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int sender = rs.getInt("sender_id");
                    int receiver = rs.getInt("receiver_id");

                    int otherUser = (sender == userId) ? receiver : sender;
                    list.add("Connected with User ID: " + otherUser);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // VIEW PENDING REQUESTS WITH USERNAME
    public List<String> getPendingRequests(int userId) {
        List<String> list = new ArrayList<>();

        String sql = """
        SELECT c.id AS request_id, u.username AS sender_name
        FROM connections c
        JOIN users u ON c.sender_id = u.id
        WHERE c.receiver_id = ?
          AND c.status = 'PENDING'
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int requestId = rs.getInt("request_id");
                    String senderName = rs.getString("sender_name");

                    list.add("Request ID: " + requestId + " | From: " + senderName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean removeConnection(int userId1, int userId2) {
        String sql = "DELETE FROM connections " +
                "WHERE ((sender_id = ? AND receiver_id = ?) " +
                "OR (sender_id = ? AND receiver_id = ?)) " +
                "AND status = 'ACCEPTED'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId1);
            ps.setInt(2, userId2);
            ps.setInt(3, userId2);
            ps.setInt(4, userId1);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
