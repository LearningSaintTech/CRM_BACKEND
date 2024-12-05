package com.example.springsocial.DTO;
import java.util.List;

public class UserStoriesDTO {
    private Long userId; // User ID
    private String userName; // User Name
    private List<StoryDTO> stories; // List of stories for this user

    // Constructor
    public UserStoriesDTO(Long userId, String userName, List<StoryDTO> stories) {
        this.userId = userId;
        this.userName = userName;
        this.stories = stories;
    }

    // Getters and setters
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

    public List<StoryDTO> getStories() {
        return stories;
    }

    public void setStories(List<StoryDTO> stories) {
        this.stories = stories;
    }
}

