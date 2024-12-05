package com.example.springsocial.model;


import java.time.LocalDateTime;

public class MessageRequest {
    private Integer conversationId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDateTime timestamp;

    // No-args constructor
    public MessageRequest() {
    }

    // All-args constructor
    public MessageRequest(Integer conversationId, Long senderId, Long receiverId, String message, LocalDateTime timestamp) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // toString method
    @Override
    public String toString() {
        return "MessageRequest{" +
                "conversationId=" + conversationId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

