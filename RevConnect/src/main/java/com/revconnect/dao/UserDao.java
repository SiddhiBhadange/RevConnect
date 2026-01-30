package com.revconnect.dao;

import com.revconnect.database.config.DatabaseConnection;
import com.revconnect.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean register(User user) {
        try (Connection con = DatabaseConnection.getConnection()) {

            if (con == null) return false;

            // Check first
            String checkSql = "SELECT 1 FROM users WHERE username = ? OR email = ?";
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setString(1, user.getUsername());
                checkPs.setString(2, user.getEmail());

                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Username or Email already exists. Try another.");
                        return false;
                    }
                }
            }

            // Insert if safe
            String sql = "INSERT INTO users(username, email, password, user_type) VALUES(?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getUserType());

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public User login(String input, String password) {
        try(Connection con=DatabaseConnection.getConnection()){
            String sql = "SELECT * FROM users WHERE (email = ? OR username = ?) AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, input);
            ps.setString(2, input);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setUserType(rs.getString("user_type"));
                return u;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean userExists(int userId) {
        String sql = "SELECT id FROM users WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }

}
