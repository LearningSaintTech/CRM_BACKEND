package com.example.springsocial.servicesImpl;


import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springsocial.model.Conversation;
import com.example.springsocial.model.Message;
import com.example.springsocial.model.MessageRequest;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ConversationResponse;
import com.example.springsocial.payload.MessageResponse;
import com.example.springsocial.payload.WebSocketResponse;
import com.example.springsocial.repository.ConversationRepository;
import com.example.springsocial.repository.MessageRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.MessageSocketService;
/**
 * Implementation of the MessageSocketService interface that handles real-time messaging functionality using web sockets.
 */
@Service
public class MessageSocketServiceImpl implements MessageSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    // Constructor
    public MessageSocketServiceImpl(SimpMessagingTemplate messagingTemplate, UserRepository userRepository,
                                     ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Send user conversations to a specific user by their user ID through a web socket.
     *
     * @param userId The ID of the user for whom to send conversations.
     */
    @Override
    public void sendUserConversationByUserId(Long userId) {
        List<ConversationResponse> conversation = conversationRepository.findConversationsByUserId(userId);
        messagingTemplate.convertAndSend(
                "/topic/user/" + userId,
                new WebSocketResponse("ALL", conversation)
        );
    }

    /**
     * Send messages of a specific conversation to the connected users through a web socket.
     *
     * @param conversationId The ID of the conversation for which to send messages.
     */
    @Override
    public void sendMessagesByConversationId(int conversationId) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        List<Message> messageList = messageRepository.findAllByConversation(conversation);
        List<MessageResponse> messageResponseList = messageList.stream()
                .map(message -> new MessageResponse(
                        message.getMessageId(),
                        message.getSender().getId(),
                        message.getReceiver().getId(),
                        message.getMessage(),
                        Date.from(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant())
                ))
                .collect(Collectors.toList());
        messagingTemplate.convertAndSend(
                "/topic/conv/" + conversationId,
                new WebSocketResponse("ALL", messageResponseList)
        );
    }

    /**
     * Save a new message using a web socket.
     *
     * @param msg The MessageRequest object containing the message details to be saved.
     */
    @Override
    public void saveMessage(MessageRequest msg) {
        User sender = userRepository.findById(msg.getSenderId()).orElseThrow();
        User receiver = userRepository.findById(msg.getReceiverId()).orElseThrow();
        Conversation conversation = conversationRepository.findConversationByUsers(sender, receiver).orElseThrow();

        Message newMessage = new Message();
        newMessage.setMessage(msg.getMessage());
        newMessage.setTimestamp(msg.getTimestamp());
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);

        Message savedMessage = messageRepository.save(newMessage);

        // Notify listener
        MessageResponse res = new MessageResponse(
                savedMessage.getMessageId(),
                savedMessage.getSender().getId(),
                savedMessage.getReceiver().getId(),
                savedMessage.getMessage(),
                Date.from(savedMessage.getTimestamp().atZone(ZoneId.systemDefault()).toInstant())
        );

        messagingTemplate.convertAndSend(
                "/topic/conv/" + msg.getConversationId(),
                new WebSocketResponse("ADDED", res)
        );

        sendUserConversationByUserId(msg.getSenderId());
        sendUserConversationByUserId(msg.getReceiverId());
    }

    /**
     * Delete a conversation by its unique conversation ID using a web socket.
     *
     * @param conversationId The ID of the conversation to be deleted.
     */
    @Transactional
    @Override
    public void deleteConversationByConversationId(int conversationId) {
        Conversation c = new Conversation();
        c.setConversationId(conversationId);
        messageRepository.deleteAllByConversation(c);
        conversationRepository.deleteById(conversationId);
    }

    /**
     * Delete a message by its unique message ID within a conversation using a web socket.
     *
     * @param conversationId The ID of the conversation to notify its listener.
     * @param messageId      The ID of the message to be deleted.
     */
    @Override
    public void deleteMessageByMessageId(int conversationId, int messageId) {
        messageRepository.deleteById(messageId);
        // Notify listener
        sendMessagesByConversationId(conversationId);
    }
    
    public List<Message> getMessagesByConversationIdSortedByTimestamp(int conversationId) {
        // Assuming the MessageRequest entity has a timestamp field.
        return messageRepository.findAllByConversation_ConversationIdOrderByTimestamp(conversationId);
    }
}

