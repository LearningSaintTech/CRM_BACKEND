package com.example.springsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsocial.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Custom query methods can be added here if needed
}

