package com.example.springsocial.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private int conversationId;

    @ManyToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User user2;

    // No-args constructor
    public Conversation() {
    }

    // All-args constructor
    public Conversation(int conversationId, User user1, User user2) {
        this.conversationId = conversationId;
        this.user1 = user1;
        this.user2 = user2;
    }

    // Getters and Setters
    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    // toString method
    @Override
    public String toString() {
        return "UserConversations{" +
                "conversationId=" + conversationId +
                ", user1=" + user1 +
                ", user2=" + user2 +
                '}';
    }
}

