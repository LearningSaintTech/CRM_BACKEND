package com.example.springsocial.servicesImpl;



import com.example.springsocial.model.User;
import com.example.springsocial.model.UserProfile;
import com.example.springsocial.repository.UserProfileRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // Save or update a UserProfile
    public UserProfile saveOrUpdateUserProfile(Long userId, UserProfile userProfile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Set the user in the userProfile before saving
        userProfile.setUser(user);

        // Set the bi-directional relationship
        user.setUserProfile(userProfile);

        return userProfileRepository.save(userProfile);
    }


    public UserProfile getUserProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Retrieve and return the associated UserProfile
        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            throw new EntityNotFoundException("UserProfile not found for User ID: " + userId);
        }
        
        return userProfile;
    }
    
    public List<UserProfile> getNearbyUsers(Long userId, Double latitude, Double longitude, Double radius) {
        return userProfileRepository.findNearbyUsersExcludingCurrentUser(userId, latitude, longitude, radius);
    }
}
