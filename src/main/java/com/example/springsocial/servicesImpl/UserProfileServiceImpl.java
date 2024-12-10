package com.example.springsocial.servicesImpl;



import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.User;
import com.example.springsocial.model.UserProfile;
import com.example.springsocial.repository.UserProfileRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.UserProfileService;

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
        
        user.setName(userProfile.getName());
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
            existingProfile.setLocation(userProfile.getLocation());
            existingProfile.setPronouns(userProfile.getPronouns());
            existingProfile.setRelationshipGoals(userProfile.getRelationshipGoals());
            existingProfile.setInterests(userProfile.getInterests());
            existingProfile.setImageData(userProfile.getImageData());
         
         
            
            // Update the image if provided
            if (userProfile.getImageData() != null) {
                existingProfile.setImageData(userProfile.getImageData());
            }
            if(user.getProfileFlag()==false)
            {
            	System.out.println("update the flag");
            user.setProfileFlag(true);
            userRepository.save(user);
            }
            return userProfileRepository.save(existingProfile);
        } else {
            userProfile.setUser(user);
            
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
    
    public void updateUserLocation(Long userId, Double latitude, Double longitude) {
        // Fetch the UserProfile for the given userId
        UserProfile userProfile = userProfileRepository.findByUserId(userId);

        if (userProfile == null) {
            throw new IllegalStateException("UserProfile not found for user ID: " + userId);
        }
        userProfile.setCurrentLatitude(latitude);
        userProfile.setCurrentLongitude(longitude);

        // Save the updated profile
         userProfileRepository.save(userProfile);
    }

}
