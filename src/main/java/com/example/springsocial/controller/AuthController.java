package com.example.springsocial.controller;

import com.example.springsocial.exception.BadRequestException;
import com.example.springsocial.model.AuthProvider;
import com.example.springsocial.model.Role;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.SignUpRequest;
import com.example.springsocial.repository.RoleRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.TokenProvider;
import com.example.springsocial.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private EmailService emailService;
    private Map<String, String> otpStorage = new HashMap<>(); // Temporary storage for OTPs

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setProvider(AuthProvider.local);
        user.setStatus("inactive");

        // Assign default role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BadRequestException("User Role not set."));
        user.setRoles(Collections.singletonList(userRole));
        String otp = String.valueOf((int)(Math.random() * 9000) + 1000); // Generate a random 4-digit OTP
        user.setOtp(otp);
        User result = userRepository.save(user);
        
        

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
       
        try {
            emailService.sendRegistrationEmail(user.getEmail(), user.getName(), otp);
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }
        
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }
    
    @GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        System.out.println("Received email: " + email);
        System.out.println("Received OTP: " + otp);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found."));
        System.out.println("Stored OTP: " + user.getOtp());

        if (user.getOtp().equals(otp)) {
            user.setEmailVerified(true);
            user.setOtp(null); // Clear the OTP after successful verification
            user.setStatus("active");
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse(true, "Email verified successfully."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid OTP."));
        }
    }


}
