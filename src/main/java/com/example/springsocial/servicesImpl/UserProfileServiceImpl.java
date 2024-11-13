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
        
        // Retrieve the existing UserProfile
        UserProfile existingProfile = userProfileRepository.findByUserId(userId);
        
        if (existingProfile != null) {
            // Update the fields of the existing UserProfile
            existingProfile.setAboutYou(userProfile.getAboutYou());
            existingProfile.setAge(userProfile.getAge());
            existingProfile.setBirthday(userProfile.getBirthday());
            existingProfile.setCurrentLatitude(userProfile.getCurrentLatitude());
            existingProfile.setCurrentLongitude(userProfile.getCurrentLongitude());
            existingProfile.setGender(userProfile.getGender());
            existingProfile.setName(userProfile.getName());
            
            // Update the image if provided
            if (userProfile.getImageData() != null) {
                existingProfile.setImageData(userProfile.getImageData());
            }

            // Save the updated profile
            return userProfileRepository.save(existingProfile);
        } else {
            // Set the user in the userProfile before saving
            userProfile.setUser(user);
            
            // Save as a new profile if it doesn't exist
            return userProfileRepository.save(userProfile);
        }
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
