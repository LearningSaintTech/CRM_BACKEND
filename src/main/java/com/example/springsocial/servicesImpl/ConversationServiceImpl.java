package com.example.springsocial.servicesImpl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.Conversation;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ConversationResponse;
import com.example.springsocial.repository.ConversationRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository,UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;

        
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
  
    
    
    @Transactional
    public Integer createOrGetConversation(Long user1Id, Long user2Id) {
        // Check if the conversation already exists
        return conversationRepository.findByUser1IdAndUser2IdOrUser2IdAndUser1Id(user1Id, user2Id, user1Id, user2Id)
                .map(Conversation::getConversationId)
                .orElseGet(() -> {
                    // Fetch users from the database
                    User user1 = userRepository.findById(user1Id)
                            .orElseThrow(() -> new IllegalArgumentException("User1 not found with ID: " + user1Id));
                    User user2 = userRepository.findById(user2Id)
                            .orElseThrow(() -> new IllegalArgumentException("User2 not found with ID: " + user2Id));

                    // Create and save a new conversation
                    Conversation conversation = new Conversation();
                    conversation.setUser1(user1);
                    conversation.setUser2(user2);
                    Conversation savedConversation = conversationRepository.save(conversation);

                    // Return the conversation ID
                    return savedConversation.getConversationId();
                });
    }

}

