package com.example.springsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsocial.model.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
}

