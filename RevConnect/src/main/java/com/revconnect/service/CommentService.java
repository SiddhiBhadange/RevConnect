package com.revconnect.service;

import com.revconnect.dao.CommentDao;
import com.revconnect.model.Comment;

import java.util.List;

public class CommentService {

    private final CommentDao dao = new CommentDao();

    public boolean addComment(Comment comment) {
        return dao.addComment(comment);
    }

    public List<Comment> getCommentsByPost(int postId) {
        return dao.getCommentsByPost(postId);
    }
}

