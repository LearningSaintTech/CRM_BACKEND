package com.example.springsocial.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springsocial.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

	
	@Query("SELECT s FROM Story s WHERE " +
		       "6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
		       "cos(radians(s.longitude) - radians(:longitude)) + " +
		       "sin(radians(:latitude)) * sin(radians(s.latitude))) <= :radius " +
		       "AND s.expirationAt > CURRENT_TIMESTAMP")
		List<Story> findStoriesWithinRadiusAndNotExpired(
		        @Param("latitude") Double latitude,
		        @Param("longitude") Double longitude,
		        @Param("radius") Double radius);
	
	
	@Query("SELECT s FROM Story s WHERE " +
		       "6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
		       "cos(radians(s.longitude) - radians(:longitude)) + " +
		       "sin(radians(:latitude)) * sin(radians(s.latitude))) <= :radius " +
		       "AND s.expirationAt > CURRENT_TIMESTAMP " +
		       "AND s.user.id = :userId")
		List<Story> findAllStoriesByUserIdWithinRadiusAndNotExpired(
		        @Param("userId") Long userId,
		        @Param("latitude") Double latitude,
		        @Param("longitude") Double longitude,
		        @Param("radius") Double radius);
	
	
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Story s WHERE s.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
	
}
