package com.example.springsocial.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "swipes")
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "swiper_id", nullable = false)
    private User swiper;

    @ManyToOne
    @JoinColumn(name = "swipee_id", nullable = false)
    private User swipee;

    @Column(nullable = false)
    private String action; // "like" or "dislike"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Swipe() {
        this.createdAt = LocalDateTime.now();
    }

    public Swipe(User swiper, User swipee, String action) {
        this.swiper = swiper;
        this.swipee = swipee;
        this.action = action;
        this.createdAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSwiper() {
		return swiper;
	}

	public void setSwiper(User swiper) {
		this.swiper = swiper;
	}

	public User getSwipee() {
		return swipee;
	}

	public void setSwipee(User swipee) {
		this.swipee = swipee;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    // Getters and Setters
    
}

