package com.example.springsocial.services;


import com.example.springsocial.model.Conversation;
import com.example.springsocial.payload.ConversationResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> getConversationsByUserId(Long userId);
    
    public Integer createOrGetConversation(Long user1Id, Long user2Id);

}

