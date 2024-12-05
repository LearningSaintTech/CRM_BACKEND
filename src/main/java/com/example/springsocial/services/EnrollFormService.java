package com.example.springsocial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsocial.model.EnrollForm;
import com.example.springsocial.repository.EnrollFormRepository;
import com.example.springsocial.repository.PhotoRepository;

import java.util.List;

@Service
public class EnrollFormService {

    private final EnrollFormRepository enrollFormRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public EnrollFormService(EnrollFormRepository enrollFormRepository, PhotoRepository photoRepository) {
        this.enrollFormRepository = enrollFormRepository;
        this.photoRepository = photoRepository;
    }

    // Save a new EnrollForm along with its photos
    public EnrollForm saveEnrollForm(EnrollForm enrollForm) {
        // Save EnrollForm entity first (cascade will save related photos if using CascadeType.ALL)
        return enrollFormRepository.save(enrollForm);
    }

    // Get all EnrollForms
    public List<EnrollForm> getAllEnrollForms() {
        return enrollFormRepository.findAll();
    }

    // Find EnrollForm by ID
    public EnrollForm getEnrollFormById(Long id) {
        return enrollFormRepository.findById(id).orElse(null);
    }

    // Delete EnrollForm by ID
    public void deleteEnrollForm(Long id) {
        enrollFormRepository.deleteById(id);
    }
}
