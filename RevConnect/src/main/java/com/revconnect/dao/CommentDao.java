package com.revconnect.dao;

import com.revconnect.database.config.DatabaseConnection;
import com.revconnect.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO comments(post_id, user_id, content) VALUES(?,?,?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, comment.getPostId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getCommentsByPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id=? ORDER BY created_at ASC";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setPostId(rs.getInt("post_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setContent(rs.getString("content"));
                c.setCreatedAt(rs.getTimestamp("created_at"));

                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }
}

