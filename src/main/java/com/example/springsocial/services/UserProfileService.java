package com.example.springsocial.services;

import java.util.List;

import com.example.springsocial.model.UserProfile;

public interface UserProfileService {
    public UserProfile saveOrUpdateUserProfile(Long userId, UserProfile userProfile) ;

	public UserProfile getUserProfileByUserId(Long userId);

    public List<UserProfile> getNearbyUsers(Long userId, Double latitude, Double longitude, Double radius) ;
}
