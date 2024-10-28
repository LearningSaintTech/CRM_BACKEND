package com.example.springsocial.repository;

import com.example.springsocial.model.UserProfile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	
	@Query("SELECT u FROM UserProfile u WHERE u.user.id <> :userId " +
		       "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(u.currentLatitude)) * " +
		       "cos(radians(u.currentLongitude) - radians(:longitude)) + " +
		       "sin(radians(:latitude)) * sin(radians(u.currentLatitude)))) < :radius")
		List<UserProfile> findNearbyUsersExcludingCurrentUser(
		        @Param("userId") Long userId,
		        @Param("latitude") Double latitude,
		        @Param("longitude") Double longitude,
		        @Param("radius") Double radius);

}
