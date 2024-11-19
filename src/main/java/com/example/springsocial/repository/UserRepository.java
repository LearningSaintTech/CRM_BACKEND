package com.example.springsocial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springsocial.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT s.swipee.id FROM Swipe s WHERE s.swiper.id = :userId)")
    List<User> findUnswipedProfiles(Long userId);

}
