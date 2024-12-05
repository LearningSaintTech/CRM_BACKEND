package com.example.springsocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springsocial.model.UserMessage;
import com.example.springsocial.services.UserMessageService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/messages")
public class UserMessageController {

    @Autowired
    private UserMessageService service;

    // Create a new message (POST)
    @PostMapping
    public ResponseEntity<UserMessage> createMessage(@RequestBody UserMessage message) {
        UserMessage savedMessage = service.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    // Get all messages (GET)
    @GetMapping
    public ResponseEntity<List<UserMessage>> getAllMessages() {
        List<UserMessage> messages = service.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // Get a message by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<UserMessage> getMessageById(@PathVariable Long id) {
        return service.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

