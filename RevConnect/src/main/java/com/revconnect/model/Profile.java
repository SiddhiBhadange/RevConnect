package com.revconnect.model;

public class Profile {
    private int userId;
    private String name;

    public Profile(int userId, String name, String bio, String location, String website) {
        this.userId = userId;
        this.name = name;
        this.bio = bio;
        this.location = location;
        this.website = website;

    }

    public Profile() {
    }
    @Override
    public String toString() {
        return "Profile{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +

                '}';
    }

    private String bio;
    private String location;
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
