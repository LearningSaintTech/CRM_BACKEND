package com.example.springsocial.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsocial.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    Optional<Swipe> findBySwiperIdAndSwipeeId(Long swiperId, Long swipeeId);
}

