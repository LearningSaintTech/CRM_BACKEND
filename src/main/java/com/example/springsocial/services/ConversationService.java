package com.example.springsocial.services;


import com.example.springsocial.payload.ConversationResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> getConversationsByUserId(Long userId);
}

