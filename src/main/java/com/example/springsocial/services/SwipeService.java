package com.example.springsocial.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.Match;
import com.example.springsocial.model.Swipe;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.MatchRepository;
import com.example.springsocial.repository.SwipeRepository;
import com.example.springsocial.repository.UserRepository;

@Service
public class SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    public void handleSwipe(Long swiperId, Long swipeeId, String action) {
        // Save the swipe
        User swiper = userRepository.findById(swiperId).orElseThrow();
        User swipee = userRepository.findById(swipeeId).orElseThrow();
        Swipe swipe = new Swipe(swiper, swipee, action);
        swipeRepository.save(swipe);

        if ("like".equalsIgnoreCase(action)) {
            // Check for a mutual like
            Optional<Swipe> mutualSwipe = swipeRepository.findBySwiperIdAndSwipeeId(swipeeId, swiperId);
            if (mutualSwipe.isPresent() && "like".equalsIgnoreCase(mutualSwipe.get().getAction())) {
                // Create a match
                Match match = new Match(swiper, swipee);
                matchRepository.save(match);
            }
        }
    }

    public List<User> getProfilesForUser(Long userId) {
        return userRepository.findUnswipedProfiles(userId);
    }
}

