package com.example.springsocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsocial.model.User;
import com.example.springsocial.services.SwipeService;

@RestController
@RequestMapping("/api")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    @PostMapping("/swipe")
    public ResponseEntity<String> swipe(
            @RequestParam Long swiperId,
            @RequestParam Long swipeeId,
            @RequestParam String action
    ) {
        swipeService.handleSwipe(swiperId, swipeeId, action);
        return ResponseEntity.ok("Swipe recorded successfully");
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<User>> getProfiles(@RequestParam Long userId) {
        List<User> profiles = swipeService.getProfilesForUser(userId);
        return ResponseEntity.ok(profiles);
    }
}

