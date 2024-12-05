package com.example.springsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsocial.model.EnrollForm;


public interface EnrollFormRepository extends JpaRepository<EnrollForm, Long> {
}

