package com.example.springsocial.services;

public interface EmailService {
	public void sendRegistrationEmail(String toEmail, String name, String otp) ;
	public void sendPasswordResetEmail(String email, String otp) ;


}
