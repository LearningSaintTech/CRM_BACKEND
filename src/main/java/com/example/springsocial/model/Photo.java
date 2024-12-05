package com.example.springsocial.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] imageData;  // Store image as byte array

    @ManyToOne
    @JoinColumn(name = "enroll_form_id")
    private EnrollForm enrollForm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public EnrollForm getEnrollForm() {
		return enrollForm;
	}

	public void setEnrollForm(EnrollForm enrollForm) {
		this.enrollForm = enrollForm;
	}

    // Getters and setters
    
}

