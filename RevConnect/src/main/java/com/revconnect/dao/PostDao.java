package com.revconnect.dao;

import com.revconnect.database.config.DatabaseConnection;
import com.revconnect.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    // CREATE POST
    public boolean createPost(Post post) {
        String sql = "INSERT INTO posts(user_id, content, hashtags) VALUES(?,?,?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            if (con == null) return false;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getHashtags());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET POSTS BY USER
    public List<Post> getPostsByUser(int userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getString("hashtags"),
                        rs.getTimestamp("created_at")
                );
                posts.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    // DELETE POST
    public boolean deletePost(int postId, int userId) {
        String sql = "DELETE FROM posts WHERE id = ? AND user_id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // EDIT POST (Only Owner Can Edit)
    public boolean updatePost(int postId, int userId, String content, String hashtags) {
        String sql = "UPDATE posts SET content = ?, hashtags = ? WHERE id = ? AND user_id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            if (con == null) return false;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, content);
            ps.setString(2, hashtags);
            ps.setInt(3, postId);
            ps.setInt(4, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

