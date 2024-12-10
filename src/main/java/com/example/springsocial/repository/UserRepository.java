package com.example.springsocial.repository;

import com.example.springsocial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findById(Long Id);


    Boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.id <> :userId")
    List<User> findAllUsersExceptThisUserId(@Param("userId") Long userId);
    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT s.swipee.id FROM Swipe s WHERE s.swiper.id = :userId)")
    List<User> findUnswipedProfiles(Long userId);

}
