package com.revconnect.service;

import com.revconnect.dao.LikeDAO;

public class LikeService {
    private LikeDAO likeDAO = new LikeDAO();


    public void like(int userId, int postId) {
        likeDAO.likePost(userId, postId);
    }


    public void unlike(int userId, int postId) {
        likeDAO.unlikePost(userId, postId);
    }
}