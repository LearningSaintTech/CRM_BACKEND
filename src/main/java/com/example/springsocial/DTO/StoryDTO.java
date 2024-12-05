package com.example.springsocial.DTO;


public class StoryDTO {
    private Long storyId;
    private String title;
    private Double latitude;
    private Double longitude;
    private byte[] file; // File image as byte array
    private Long userId; // User ID
    private String userName; // User name

    // Constructor
    public StoryDTO(Long storyId, String title, Double latitude, Double longitude, byte[] file, Long userId, String userName) {
        this.storyId = storyId;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.file = file;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and setters
    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

