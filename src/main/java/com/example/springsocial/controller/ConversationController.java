package com.example.springsocial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsocial.DTO.ConversationResponseDto;
import com.example.springsocial.model.Message;
import com.example.springsocial.payload.ConversationResponse;
import com.example.springsocial.services.ConversationService;
import com.example.springsocial.services.MessageSocketService;

@RestController
@RequestMapping("/abcd")
public class ConversationController {

    private final ConversationService conversationService;
    
    private final MessageSocketService socketService;


    @Autowired
    public ConversationController(ConversationService conversationService,MessageSocketService socketService) {
    
        this.socketService = socketService;
        this.conversationService = conversationService;
    }

    @GetMapping("/conversations")
    public List<ConversationResponse> getConversations(@RequestParam Long userId) {
        System.out.println("Fetching conversations for user ID: {}"+userId);
        List<ConversationResponse> conversations = conversationService.getConversationsByUserId(userId);
        System.out.println("Fetched Conversations: {}"+conversations);
        return conversations;
    }
    @GetMapping("/conversations/a")
    public void getConnversations() {
        System.out.println("Fetching conversations for user ID: ");
        
        
    }
    

    @GetMapping("/getMessagesByTimestamp")
    public List<ConversationResponseDto> getMessagesByConversationIdAndTimestamp(@RequestParam(required = true) Integer conversationId) {
        System.out.println("Fetching messages for conversationId: " + conversationId);
        
        // Fetch the messages sorted by timestamp
        List<Message> messages = socketService.getMessagesByConversationIdSortedByTimestamp(conversationId);
        
        // Transform messages to ConversationResponseDto
        List<ConversationResponseDto> conversationResponseDtos = messages.stream()
                .map(message -> {
                    ConversationResponseDto dto = new ConversationResponseDto();
                    dto.setConversationId(message.getConversation().getConversationId());
                    
                    // Extracting the details for the other user (assuming user2 is the other user)
                    dto.setOtherUserId(message.getConversation().getUser2().getId());
                    dto.setOtherUserName(message.getConversation().getUser2().getName());
                    dto.setOtherUserImage(message.getReceiver().getImageData());
                    
                    // Set the last message details
                    dto.setLastMessage(message.getMessage());
                    dto.setLastMessageTimestamp(message.getTimestamp());
                    
                    // Set sender and receiver IDs
                    dto.setSenderId(message.getSender().getId());
                    dto.setReceiverId(message.getReceiver().getId());
                    dto.setSenderName(message.getSender().getName());
                    dto.setReciverName(message.getReceiver().getName());
                    
                    return dto;
                })
                .collect(Collectors.toList());

        return conversationResponseDtos;
    }

}
