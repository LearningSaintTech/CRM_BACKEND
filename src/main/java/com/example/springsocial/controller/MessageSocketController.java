package com.example.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.springsocial.model.Message;
import com.example.springsocial.model.MessageRequest;
import com.example.springsocial.services.MessageSocketService;

import java.util.List;
import java.util.Map;


@Controller
public class MessageSocketController {

    private final MessageSocketService socketService;

    public MessageSocketController(MessageSocketService socketService) {
        this.socketService = socketService;
    }

    /**
     * Send user conversations to a specific user by their user ID through a web socket.
     *
     * @param userId The ID of the user for whom to send conversations.
     */
    @MessageMapping("/user")
    public void sendUserConversationByUserId(Long userId) {
        System.out.println("Sending user conversations for userId: " + userId);
        socketService.sendUserConversationByUserId(userId);
    }

    /**
     * Send messages of a specific conversation to the connected users through a web socket.
     *
     * @param conversationId The ID of the conversation for which to send messages.
     */
    @MessageMapping("/conv")
    public void sendMessagesByConversationId(int conversationId) {
        System.out.println("Sending messages for conversationId: " + conversationId);
        socketService.sendMessagesByConversationId(conversationId);
    }

    /**
     * Save a new message using a web socket.
     *
     * @param message The MessageRequest object containing the message details to be saved.
     */
    @MessageMapping("/sendMessage")
    public void saveMessage(MessageRequest message) {
        System.out.println("Received message: " + message);
        socketService.saveMessage(message);
    }

    /**
     * Delete a conversation by its unique conversation ID using a web socket.
     *
     * @param payload A Map containing the conversationId, user1Id, and user2Id for the
     *                conversation to be deleted and notify listener.
     */
    @MessageMapping("/deleteConversation")
    public void deleteConversation(Map<String, Object> payload) {
        int conversationId = (int) payload.get("conversationId");
        Long user1 = (Long) payload.get("user1Id");
        Long user2 = (Long) payload.get("user2Id");

        System.out.println("Deleting conversationId: " + conversationId);
        System.out.println("Notifying user1: " + user1 + ", user2: " + user2);

        socketService.deleteConversationByConversationId(conversationId);
        socketService.sendUserConversationByUserId(user1);
        socketService.sendUserConversationByUserId(user2);
    }

    /**
     * Delete a message by its unique message ID within a conversation using a web socket.
     *
     * @param payload A Map containing the conversationId and messageId for the message
     *                to be deleted and notify listener.
     */
    @MessageMapping("/deleteMessage")
    public void deleteMessage(Map<String, Object> payload) {
        int conversationId = (int) payload.get("conversationId");
        int messageId = (int) payload.get("messageId");

        System.out.println("Deleting messageId: " + messageId + " from conversationId: " + conversationId);

        socketService.deleteMessageByMessageId(conversationId, messageId);
    }
    
    
    
}
