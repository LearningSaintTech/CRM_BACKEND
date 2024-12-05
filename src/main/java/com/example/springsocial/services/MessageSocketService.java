package com.example.springsocial.services;

import java.util.List;

import com.example.springsocial.model.Message;
import com.example.springsocial.model.MessageRequest;


public interface MessageSocketService {

   
    void sendUserConversationByUserId(Long userId);

   
    void sendMessagesByConversationId(int conversationId);

   
    void saveMessage(MessageRequest msg);

   
    void deleteConversationByConversationId(int conversationId);

   
    void deleteMessageByMessageId(int conversationId, int messageId);
    public List<Message> getMessagesByConversationIdSortedByTimestamp(int conversationId) ;

}
