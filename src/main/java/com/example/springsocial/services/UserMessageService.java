package com.example.springsocial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.UserMessage;
import com.example.springsocial.repository.UserMessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserMessageService {
    @Autowired
    private UserMessageRepository repository;

    public UserMessage saveMessage(UserMessage message) {
        return repository.save(message);
    }

    public List<UserMessage> getAllMessages() {
        return repository.findAll();
    }

    public Optional<UserMessage> getMessageById(Long id) {
        return repository.findById(id);
    }
}

