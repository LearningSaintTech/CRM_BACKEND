package com.example.springsocial.controller;

import com.example.springsocial.model.UserProfile;
import com.example.springsocial.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // POST API to create or update a UserProfile
    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserProfile> createOrUpdateUserProfile(
            @PathVariable Long userId,
            @RequestPart("userProfile") String userProfileJson,
            @RequestParam("image") MultipartFile image) throws IOException {
        
        // Deserialize JSON string to UserProfile object (using ObjectMapper)
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule());
    	UserProfile userProfile = objectMapper.readValue(userProfileJson, UserProfile.class);

        if (image != null && !image.isEmpty()) {
            userProfile.setImageData(image.getBytes());
        }

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
    public List<UserProfile> getNearbyUsers(
            @RequestParam("userId") Long userId,  // Add userId parameter
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "radius", defaultValue = "10") Double radius) {

        // Pass userId to the service to filter it out
        List<UserProfile> nearbyUsers = userProfileService.getNearbyUsers(userId, latitude, longitude, radius);
        System.out.println("User ID: inside controller      dsjcjdndrnj " );

        nearbyUsers.forEach(userProfile -> {
            System.out.println("User ID: " + userProfile.getId());
            System.out.println("Name: " + userProfile.getUser().getName());
            System.out.println("Latitude: " + userProfile.getCurrentLatitude());
            System.out.println("Longitude: " + userProfile.getCurrentLongitude());
            System.out.println("----------");
        });
        
        return nearbyUsers;
    }

}
