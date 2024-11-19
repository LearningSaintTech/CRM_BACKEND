package com.example.springsocial.model;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String title;
	
    private Double latitude;  // Latitude for the story's location
    
    private Double longitude; // Longitude for the story's location
    
    private LocalDateTime createdAt; // Timestamp for story creation
    private LocalDateTime expirationAt; // Timestamp for story expiration

    
    // Many-to-one relationship with User
    @ManyToOne(fetch = FetchType.LAZY)  // LAZY loading is a good practice to avoid unnecessary loading of the user
    @JoinColumn(name = "user_id")  // This will create a foreign key column named user_id in the stories table
    @JsonIgnore  // Prevent circular references when serializing to JSON (avoid infinite recursion between User and Story)
    private User user;
    

   
    @Lob  // Use @Lob to store large data (like images or files) lob is used for storing data in byte
    private byte[] file;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Automatically set the timestamp
        System.out.println("Helllooooooo>>>>>"+createdAt);
        expirationAt = createdAt.plusHours(24); // Set expiration time to 24 hours after creation
    }

    public LocalDateTime getExpirationAt() {
        return expirationAt;
    }

    
    public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpirationAt(LocalDateTime expirationAt) {
        this.expirationAt = expirationAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCurrentLatitude() {
		// TODO Auto-generated method stub
		return null;
	}

   
	
    
}
