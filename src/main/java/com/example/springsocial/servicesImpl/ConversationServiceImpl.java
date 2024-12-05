package com.example.springsocial.servicesImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.payload.ConversationResponse;
import com.example.springsocial.repository.ConversationRepository;
import com.example.springsocial.services.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(Long userId) {
        // Fetch the conversations from the repository
        List<ConversationResponse> conversations = conversationRepository.findConversationsByUserId(userId);

        // Print the fetched conversations to the console
        System.out.println("Fetched Conversations for userId " + userId + ": " + conversations);

        // Return the result
        return conversations;
    }

}

