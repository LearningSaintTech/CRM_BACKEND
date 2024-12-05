package com.example.springsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsocial.model.Conversation;
import com.example.springsocial.model.Message;
import com.example.springsocial.model.MessageRequest;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByConversation(Conversation conversation);

    void deleteAllByConversation(Conversation conversation);
    
    List<Message> findAllByConversation_ConversationIdOrderByTimestamp(int conversationId);

}
