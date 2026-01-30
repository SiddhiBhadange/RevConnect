package com.revconnect.service;

import com.revconnect.dao.FollowDAO;

import java.util.List;

public class FollowService {

    private final FollowDAO followDAO = new FollowDAO();

    public boolean followUser(int followerId, int followingId) {
       return followDAO.follow(followerId, followingId);
    }

    public boolean unfollowUser(int followerId, int followingId) {
     return followDAO.unfollow(followerId, followingId);
    }

    public List<Integer> getFollowers(int userId) {
        return followDAO.getFollowers(userId);
    }

    public List<Integer> getFollowing(int userId) {
        return followDAO.getFollowing(userId);
    }
}
