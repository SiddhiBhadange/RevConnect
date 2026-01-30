package com.revconnect.model;
import java.sql.Timestamp;

public class Follow {

    private int followerId;
    private int followingId;
    private Timestamp createdAt;

    // Constructors
    public Follow() {}

    public Follow(int followerId, int followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    // Getters & Setters
    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Follow {" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                ", createdAt=" + createdAt +
                '}';
    }
}
