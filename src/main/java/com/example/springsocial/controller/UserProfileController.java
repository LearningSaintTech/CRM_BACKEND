package com.example.springsocial.controller;

import com.example.springsocial.model.UserProfile;
import com.example.springsocial.services.UserProfileService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // POST API to create or update a UserProfile
    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserProfile> createOrUpdateUserProfile(@PathVariable Long userId, @RequestBody UserProfile userProfile) {
    	System.out.println("inside createOrUpdateUserProfile"+userId);
        UserProfile savedProfile = userProfileService.saveOrUpdateUserProfile(userId, userProfile);
        return ResponseEntity.ok(savedProfile);
    }

    // GET API to retrieve a UserProfile by user ID
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);
        return ResponseEntity.ok(userProfile);
    }
    
    @GetMapping("/nearby")
    public ResponseEntity<List<UserProfile>> getNearbyUsers(
            @RequestParam("userId") Long userId,  // Add userId parameter
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "radius", defaultValue = "10") Double radius) {

        // Pass userId to the service to filter it out
        List<UserProfile> nearbyUsers = userProfileService.getNearbyUsers(userId, latitude, longitude, radius);
        return ResponseEntity.ok(nearbyUsers);
    }

}
