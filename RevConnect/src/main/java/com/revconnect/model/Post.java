package com.revconnect.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String hashtags;

    public Post(int id, int userId, String content, String hashtags) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.hashtags = hashtags;
    }

    private Timestamp createdAt;

    // Constructors
    public Post() {}
    public Post(int id, int userId, String content, String hashtags, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.hashtags = hashtags;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post {" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", hashtags='" + hashtags + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
