package com.example.springsocial.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.Story;
import com.example.springsocial.model.StoryView;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.StoryRepository;
import com.example.springsocial.repository.StoryViewRepository;
import com.example.springsocial.repository.UserRepository;

@Service
public class StoryViewService {

    @Autowired
    private StoryViewRepository storyViewRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    // Record a story view
    public void recordStoryView(Long storyId, Long userId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user already viewed the story
        if (!hasUserViewedStory(story, user)) {
            StoryView storyView = new StoryView();
            storyView.setStory(story);
            storyView.setUser(user);
            storyView.setViewedAt(LocalDateTime.now());
            storyViewRepository.save(storyView);
        }
    }

    // Check if a user has already viewed a story
    public boolean hasUserViewedStory(Story story, User user) {
        return storyViewRepository.findUsersWhoViewedStory(story.getId())
                .stream()
                .anyMatch(viewer -> viewer.getId().equals(user.getId()));
    }

    // Get all users who viewed a specific story
    public List<User> getUsersWhoViewedStory(Long storyId) {
        return storyViewRepository.findUsersWhoViewedStory(storyId);
    }

    // Count views for a specific story
    public Long countStoryViews(Long storyId) {
        return storyViewRepository.countViewsByStoryId(storyId);
    }
}

