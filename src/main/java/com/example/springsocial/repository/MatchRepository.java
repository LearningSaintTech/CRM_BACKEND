package com.example.springsocial.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsocial.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}

