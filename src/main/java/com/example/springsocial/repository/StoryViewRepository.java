package com.example.springsocial.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springsocial.model.StoryView;
import com.example.springsocial.model.User;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {

    // Fetch all users who viewed a specific story
    @Query("SELECT sv.user FROM StoryView sv WHERE sv.story.id = :storyId")
    List<User> findUsersWhoViewedStory(@Param("storyId") Long storyId);

    // Count views for a specific story
    @Query("SELECT COUNT(sv) FROM StoryView sv WHERE sv.story.id = :storyId")
    Long countViewsByStoryId(@Param("storyId") Long storyId);
    
    
    //20/11/24
    @Modifying
    @Transactional
    @Query("DELETE FROM StoryView sv WHERE sv.story.id = :storyId")
    void deleteByStoryId(@Param("storyId") Long storyId);
}

