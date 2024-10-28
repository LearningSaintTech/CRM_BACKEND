package com.example.springsocial.servicesImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.springsocial.services.EmailService;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;
	public void sendRegistrationEmail(String toEmail, String name, String otp) {
	    try {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");

	        // Include the OTP in the email content
	        String content = String.format("Hello %s,\n\nYour registration was successful! Welcome aboard!\n\nYour OTP for email verification is: %s", name, otp);
	        helper.setText(content, true); // 'true' tells it to treat it as HTML
	        helper.setTo(toEmail);
	        helper.setSubject("Registration Successful");
	        helper.setFrom("kukku.ashish@gmail.com"); // Replace with your email

	        // Send the email
	        mailSender.send(mimeMessage);
	    } catch (MessagingException e) {
	        e.printStackTrace(); // Handle the exception
	    }
	}

	
}
