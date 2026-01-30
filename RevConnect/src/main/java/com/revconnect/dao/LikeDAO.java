package com.revconnect.dao;

import com.revconnect.database.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LikeDAO {

    public void likePost(int userId, int postId) {

        try (Connection con = DatabaseConnection.getConnection()){
            String sql = "INSERT IGNORE INTO likes(user_id, post_id) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ps.executeUpdate();
            System.out.println("Post liked!");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void unlikePost(int userId, int postId) {

        try (Connection con = DatabaseConnection.getConnection()){
            String sql = "DELETE FROM likes WHERE user_id=? AND post_id=?";
            PreparedStatement ps = con.prepareStatement(sql);


            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ps.executeUpdate();
            System.out.println("Like removed!");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}