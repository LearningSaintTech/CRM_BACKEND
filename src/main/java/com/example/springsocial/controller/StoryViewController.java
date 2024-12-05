package com.example.springsocial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsocial.model.User;
import com.example.springsocial.servicesImpl.StoryViewService;

@RestController
@RequestMapping("/api/story-views")
public class StoryViewController {

    @Autowired
    private StoryViewService storyViewService;

    // Endpoint to record a story view
    @PostMapping("/view")
    public ResponseEntity<String> recordStoryView(
            @RequestParam Long storyId,
            @RequestParam Long userId) {
    	System.out.println("storyId"+storyId +"userId"+userId);
        storyViewService.recordStoryView(storyId, userId);
        return ResponseEntity.ok("Story view recorded");
    }

    // Endpoint to get all users who viewed a story
    @GetMapping("/viewers/{storyId}")
    public ResponseEntity<List<String>> getUsersWhoViewedStory(@PathVariable Long storyId) {
        // Get the list of users who viewed the story
        List<User> viewers = storyViewService.getUsersWhoViewedStory(storyId);
        
        // Map the list of users to a list of user names
        List<String> viewerNames = viewers.stream()
                                          .map(User::getName)  // Assuming 'getName()' returns the user's name
                                          .collect(Collectors.toList());
        
        // Print the list of viewer names to the console
        System.out.println("Viewer Names: " + viewerNames);
        
        // Optionally, use a logger for better control over logging levels
        // logger.info("Viewer Names: " + viewerNames);

        // Return the list of names in the response
        return ResponseEntity.ok(viewerNames);
    }



    // Endpoint to count story views
    @GetMapping("/count/{storyId}")
    public ResponseEntity<Long> countStoryViews(@PathVariable Long storyId) {
        Long count = storyViewService.countStoryViews(storyId);
        return ResponseEntity.ok(count);
    }
}

